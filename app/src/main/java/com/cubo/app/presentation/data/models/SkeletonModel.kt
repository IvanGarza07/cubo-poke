package com.cubo.app.presentation.data.models

import androidx.annotation.LayoutRes
import com.cubo.app.presentation.data.interfaces.ListModel

data class SkeletonModel(
    override val id: String = "",
    @LayoutRes val viewLayout: Int = 0,
    val itemRepeat: Int = 1,
    val isVerticalOrientation: Boolean = true,
    val startEndPadding: Int = 0,
    val itemSpacing: Int = 0,
    val isNeedSpanSizeOverride: Boolean = false
) : ListModel
