/*
 *  Create by Ivan Garza on 1/26/22, 5:51 PM.
 *
 *  Copyright (c) year Ivan Garza.
 *  All rights reserved.
 */

package com.cubo.app.presentation.data.models

import com.cubo.app.presentation.data.interfaces.CarouselModel

data class CarouselSkeletonModel(
    override val id: String = "",
    override val startEndPadding: Int = 0,
    override val itemSpacing: Int = 0,
    override val topBottomPadding: Int = 0
) : CarouselModel
