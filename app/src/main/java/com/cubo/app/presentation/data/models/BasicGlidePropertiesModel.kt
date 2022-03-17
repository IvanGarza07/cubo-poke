package com.cubo.app.presentation.data.models

import android.graphics.drawable.Drawable

data class BasicGlidePropertiesModel<T>(
    val path: T?,
    val placeholder: Drawable? = null,
    val isFitCenter: Boolean? = false
)
