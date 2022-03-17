package com.cubo.app.presentation.bindingAdapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.MotionEvent
import android.widget.EdgeEffect
import androidx.databinding.BindingAdapter
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringAnimation.TRANSLATION_X
import androidx.dynamicanimation.animation.SpringAnimation.TRANSLATION_Y
import androidx.dynamicanimation.animation.SpringForce
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.EdgeEffectFactory
import androidx.recyclerview.widget.RecyclerView.EdgeEffectFactory.DIRECTION_BOTTOM
import androidx.recyclerview.widget.RecyclerView.EdgeEffectFactory.DIRECTION_RIGHT
import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.stickyheader.StickyHeaderLinearLayoutManager
import com.cubo.app.presentation.utils.Constants
import com.cubo.app.presentation.views.ShimmerEpoxyRecyclerView

@BindingAdapter("startPadding", "endPadding", "topPadding", "bottomPadding", requireAll = false)
fun ShimmerEpoxyRecyclerView.setPaddingRecycler(
    paddingStart: Float = 0f,
    paddingEnd: Float = 0f,
    paddingTop: Float = 0f,
    paddingBottom: Float = 0f
) {
    recyclerView.setPadding(
        paddingStart.toInt(),
        paddingTop.toInt(),
        paddingEnd.toInt(),
        paddingBottom.toInt()
    )
}

@BindingAdapter("verticalDirection")
fun ShimmerEpoxyRecyclerView.verticalDirection(controller: EpoxyController) {
    setController(controller).apply {
        setRecyclerConfiguration(RecyclerView.VERTICAL)
    }
}

@BindingAdapter("horizontalDirection")
fun ShimmerEpoxyRecyclerView.horizontalDirection(controller: EpoxyController) {
    setController(controller).apply {
        setRecyclerConfiguration(RecyclerView.HORIZONTAL)
    }
}

fun RecyclerView.setRecyclerConfiguration(type: Int) {
    itemAnimator = DefaultItemAnimator()
    layoutManager = StickyHeaderLinearLayoutManager(context, type, false)
    setBouncyEffect(type)
}

@SuppressLint("ClickableViewAccessibility")
fun RecyclerView.setBouncyEffect(type: Int) {
    val translate = if (type == RecyclerView.HORIZONTAL) TRANSLATION_X else TRANSLATION_Y
    val orientation = if (type == RecyclerView.VERTICAL) DIRECTION_BOTTOM else DIRECTION_RIGHT
    val translation = getSpringAnimation(this, translate)
    var isTouchActive = true
    setOnTouchListener { _, event ->
        isTouchActive = event.action == MotionEvent.ACTION_DOWN
        if (event.action == MotionEvent.ACTION_UP) translation.start()
        return@setOnTouchListener false
    }

    edgeEffectFactory = getEdgeEffectFactoryObject(orientation, isTouchActive, translation, type)
}

private fun getSpringAnimation(
    recyclerView: RecyclerView,
    translate: DynamicAnimation.ViewProperty
) = SpringAnimation(recyclerView, translate)
    .setSpring(
        SpringForce()
            .setFinalPosition(0f)
            .setDampingRatio(SpringForce.DAMPING_RATIO_LOW_BOUNCY)
            .setStiffness(SpringForce.STIFFNESS_VERY_LOW)
    )

private fun getEdgeEffectFactoryObject(
    orientation: Int,
    isTouchActive: Boolean,
    translation: SpringAnimation,
    type: Int
) = object : EdgeEffectFactory() {

    override fun createEdgeEffect(recyclerView: RecyclerView, direction: Int): EdgeEffect {

        val edgeEffect = object : EdgeEffect(recyclerView.context) {

            override fun onPull(deltaDistance: Float) {
                super.onPull(deltaDistance)
                handlePull(deltaDistance)
            }

            override fun onPull(deltaDistance: Float, displacement: Float) {
                super.onPull(deltaDistance, displacement)
                handlePull(deltaDistance)
            }

            override fun onRelease() {
                super.onRelease()
                translation.start()
            }

            private fun handlePull(deltaDistance: Float) {
                // This is called on every touch event while the list is scrolled with a finger.
                // We simply update the view properties without animation.
                val sign = if (direction == orientation) -1 else 1
                val translationDelta = sign * recyclerView.width *
                        deltaDistance * Constants.OVERSCROLL_TRANSLATION_MAGNITUDE
                if (isTouchActive) translation.cancel()
                if (type == RecyclerView.VERTICAL) {
                    recyclerView.translationY += translationDelta
                } else {
                    recyclerView.translationX += translationDelta
                }
            }

            override fun onAbsorb(velocity: Int) {
                super.onAbsorb(velocity)
                val sign = if (direction == orientation) -1 else 1
                // The list has reached the edge on fling.
                val translationVelocity =
                    sign * velocity * Constants.FLING_TRANSLATION_MAGNITUDE
                translation.setStartVelocity(translationVelocity).start()
            }
        }
        edgeEffect.color = Color.WHITE
        return edgeEffect
    }
}