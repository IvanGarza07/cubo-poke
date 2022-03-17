package com.cubo.app.presentation.base.epoxy

import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.forEach
import androidx.databinding.ViewDataBinding
import com.airbnb.epoxy.DataBindingEpoxyModel
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.cubo.app.R
import com.google.android.material.imageview.ShapeableImageView
import de.hdodenhof.circleimageview.CircleImageView

@EpoxyModelClass
abstract class BaseSkeletonModel : DataBindingEpoxyModel() {

    @EpoxyAttribute
    var viewLayout = 0

    private val texts = mutableMapOf<Any, String>()

    override fun getDefaultLayout(): Int = viewLayout

    override fun setDataBindingVariables(binding: ViewDataBinding?) = Unit

    override fun bind(holder: DataBindingHolder) {
        val view = holder.dataBinding.root
        val skeletonColor = R.color.skeleton_athens_gray

        if (view is ViewGroup) {
            setViewBackgroundColor(view, skeletonColor)
        }
    }

    override fun unbind(holder: DataBindingHolder) {
        val view = holder.dataBinding.root
        if (view is ViewGroup) {
            setViewBackgroundColor(view, null)
        }
    }

    private fun setViewBackgroundColor(viewGroup: ViewGroup, color: Int?) {
        val isEnabled = color == null
        viewGroup.isEnabled = isEnabled
        val resource = color ?: android.R.color.transparent
        viewGroup.forEach { view ->
            processView(view, resource, color)
        }
    }

    private fun processView(view: View, resource: Int, color: Int?) {
        when (view) {
            is ConstraintLayout, is CardView, is FrameLayout, is LinearLayout, is RelativeLayout -> {
                if (view is ViewGroup) setViewBackgroundColor(view, color)
            }
            is CircleImageView, is ShapeableImageView -> {
                if (view is ImageView)
                    view.setImageDrawable(ContextCompat.getDrawable(view.context, resource))
            }
            else -> {
                view.setBackgroundColor(ContextCompat.getColor(view.context, resource))
                if (view is TextView) {
                    if (color != null) {
                        texts[view.id] = view.text.toString()
                        view.text = ""
                    } else {
                        view.text = texts[view.id]
                    }
                }
            }
        }
    }
}
