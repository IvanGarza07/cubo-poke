package com.cubo.app.presentation.data.models

import android.os.Parcelable
import com.cubo.app.R
import kotlinx.parcelize.Parcelize

@Parcelize
data class CarouselSettingModel(
    val color: Int = R.color.white,
    val isColor: Boolean = false,
    val isFlexbox: Boolean = false,
    val isFlexWrap: Boolean = false,
    val isJustifyCenter: Boolean = false,
    val isJustify: String = "",
    val isBouncy: Boolean = true
) : Parcelable