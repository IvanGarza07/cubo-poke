package com.cubo.app.presentation.base.epoxy

interface BaseEpoxyEventListener {

    val event: (event: BaseEpoxyClickEvent) -> Unit
}
