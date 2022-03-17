package com.cubo.app.presentation.bindingAdapters

import android.graphics.drawable.AnimationDrawable
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.cubo.app.R

@BindingAdapter("isLoading")
fun Button.initLoadingDrawable(isLoading: Boolean) {
    val loading = if (isLoading) ContextCompat.getDrawable(
        this.context,
        R.drawable.animation_button_loading
    ) else null
    isEnabled = !isLoading
    setBackgroundColor(
        ContextCompat.getColor(
            this.context,
            if (isLoading) R.color.purple_700 else R.color.purple_500
        )
    )
    setTextColor(ContextCompat.getColor(this.context, R.color.white))
    setCompoundDrawablesWithIntrinsicBounds(null, null, loading, null)
    if (isLoading) {
        val frameAnimation: AnimationDrawable = this.compoundDrawables[2] as AnimationDrawable
        frameAnimation.start()
    }
}