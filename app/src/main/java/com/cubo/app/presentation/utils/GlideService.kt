package com.cubo.app.presentation.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.cubo.app.presentation.data.models.BasicGlidePropertiesModel

class GlideService(context: Context) {

    val instance = Glide.with(context)

    fun <T> setImage(
        view: ImageView,
        model: BasicGlidePropertiesModel<T>
    ) = getDefaultBitmapConfiguration(model).into(view)

    fun setCustomBitmapTarget(
        model: BasicGlidePropertiesModel<String>,
        properties: (resource: Bitmap, transition: Transition<in Bitmap>?) -> Unit
    ) = getDefaultBitmapConfiguration(model).into(object : CustomTarget<Bitmap>() {

        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
            properties.invoke(resource, transition)
        }

        override fun onLoadCleared(placeholder: Drawable?) = Unit
    })

    fun setBitmapImageViewTarget(
        model: BasicGlidePropertiesModel<String>,
        view: ImageView,
        properties: (Bitmap?) -> Unit
    ) = getDefaultBitmapConfiguration(model)
        .into(object : BitmapImageViewTarget(view) {
            override fun setResource(resource: Bitmap?) {
                properties.invoke(resource)
            }
        })

    private fun <T> getDefaultBitmapConfiguration(
        model: BasicGlidePropertiesModel<T>
    ) = instance
        .asBitmap()
        .load(model.path)
        .placeholder(model.placeholder)
        .priority(Priority.NORMAL)
        .format(DecodeFormat.PREFER_RGB_565)
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .dontAnimate()
        .apply {
            if (model.isFitCenter == null || model.isFitCenter) fitCenter() else centerCrop()
        }
}
