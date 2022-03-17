package com.cubo.app.presentation.extensions

import android.animation.ValueAnimator
import android.content.Context
import android.text.*
import android.view.Gravity
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.cubo.app.R
import com.google.android.material.snackbar.Snackbar
import java.util.*


fun View.makeVisible() {
    visibility = View.VISIBLE
}

fun View.makeInvisible() {
    visibility = View.INVISIBLE
}

fun View.makeGone() {
    visibility = View.GONE
}

fun View.makeVisibleWithEffect() {
    if (!isVisible) {
        fadeInOutEffect(fromAlpha = 0.0f, toAlpha = 1.0f)
        makeVisible()
    }
}

fun View.makeInvisibleWithEffect() {
    if (!isInvisible) {
        this.fadeInOutEffect(fromAlpha = 1.0f, toAlpha = 0.0f)
        this.makeInvisible()
    }
}

fun View.makeGoneWithEffect() {
    if (!isGone) {
        fadeInOutEffect(fromAlpha = 1.0f, toAlpha = 0.0f)
        makeGone()
    }
}

fun View.increaseHeightEffect(startHeight: Int, endHeight: Int) {
    val anim = ValueAnimator.ofInt(startHeight, endHeight)
    anim.addUpdateListener { valueAnimator ->
        val `val` = valueAnimator.animatedValue as Int
        val layoutParams = layoutParams
        layoutParams.height = `val`
        setLayoutParams(layoutParams)
    }
    anim.duration = 500
    anim.start()
}

fun View.fadeInOutEffect(fromAlpha: Float, toAlpha: Float) {
    val anim = AlphaAnimation(fromAlpha, toAlpha)
    anim.duration = 500
    anim.repeatCount = 0
    anim.repeatMode = Animation.REVERSE
    this.startAnimation(anim)
}

fun View.hideKeyboard() {
    val inputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(rootView.windowToken, 0)
}

fun View.showSnackBar(message: String, isError: Boolean = false) {
    val snack = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    (snack.view.findViewById(com.google.android.material.R.id.snackbar_text) as TextView).also {
        it.setTextColor(ContextCompat.getColor(this.context, R.color.white))
        it.gravity = Gravity.CENTER
    }

    if (isError)
        snack.view.setBackgroundColor(ContextCompat.getColor(this.context, R.color.flamingo_500))
    else
        snack.view.setBackgroundColor(ContextCompat.getColor(this.context, R.color.purple_200))

    snack.show()
}
