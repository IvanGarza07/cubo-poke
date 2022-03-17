package com.cubo.app.presentation.bindingAdapters

import android.graphics.drawable.Drawable
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.cubo.app.presentation.data.models.BasicGlidePropertiesModel
import com.cubo.app.presentation.extensions.getKoinInstance
import com.cubo.app.presentation.utils.GlideService
import de.hdodenhof.circleimageview.CircleImageView

@BindingAdapter("app:srcCompat")
fun AppCompatImageView.setImageFromResource(image: Int) {
    setImageResource(image)
}

@BindingAdapter(value = ["srcGlide", "placeholder", "fitCenter"], requireAll = false)
fun AppCompatImageView.setImageGlide(path: String, placeholder: Drawable?, isFitCenter: Boolean = true) {
    getKoinInstance<GlideService>().setImage(
        this,
        BasicGlidePropertiesModel(path, placeholder, isFitCenter)
    )
}

@BindingAdapter(value = ["srcGlide", "placeholder", "fitCenter"], requireAll = false)
fun CircleImageView.setImageGlide(path: String, placeholder: Drawable?, isFitCenter: Boolean = true) {
    getKoinInstance<GlideService>().setImage(
        this,
        BasicGlidePropertiesModel(path, placeholder, isFitCenter)
    )
}