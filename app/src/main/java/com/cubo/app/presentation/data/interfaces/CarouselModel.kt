package com.cubo.app.presentation.data.interfaces

interface CarouselModel : ListModel {
    override val id: String
    val startEndPadding: Int
    val itemSpacing: Int
    val topBottomPadding: Int
}
