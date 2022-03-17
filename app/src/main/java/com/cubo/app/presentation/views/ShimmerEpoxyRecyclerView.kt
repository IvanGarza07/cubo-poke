package com.cubo.app.presentation.views

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.EpoxyRecyclerView
import com.cubo.app.presentation.base.epoxy.BaseSkeletonModel
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerFrameLayout

class ShimmerEpoxyRecyclerView(context: Context, attrs: AttributeSet?) : ShimmerFrameLayout(context, attrs) {

    var recyclerView = FragmentEpoxyRecyclerView(context)

    init {
        addShimmer()
        val matchParent = ViewGroup.LayoutParams.MATCH_PARENT
        addView(recyclerView, LayoutParams(matchParent, matchParent))
    }

    fun setController(controller: EpoxyController): FragmentEpoxyRecyclerView {
        recyclerView.setController(controller)
        controller.addInterceptor {
            if(it.filterIsInstance<BaseSkeletonModel>().isEmpty()) removeShimmer() else addShimmer()
        }
        return recyclerView
    }

    fun recreateRecyclerView(){
        removeView(recyclerView)
        recyclerView = FragmentEpoxyRecyclerView(context)
        addView(recyclerView, LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
    }

    fun removeShimmer() {
        setShimmer(null)
    }

    private fun addShimmer() {
        val shimmer = Shimmer
            .AlphaHighlightBuilder()
            .setRepeatDelay(600)
            .setDuration(1500)
            .setBaseAlpha(0.6f)
            .setClipToChildren(true)
            .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
            .setHighlightAlpha(1f)
            .setTilt(0.4f)
            .setAutoStart(true)
            .build()
        setShimmer(shimmer)
    }
}

class FragmentEpoxyRecyclerView(context: Context) : EpoxyRecyclerView(context) {
    override fun shouldShareViewPoolAcrossContext() = false
}
