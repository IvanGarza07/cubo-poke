package com.cubo.app.presentation.base.epoxy

sealed class BaseEpoxyCommonEvents : BaseEpoxyClickEvent {
    data class OrderDetailEvent(
        val id: String
    ) : BaseEpoxyCommonEvents()
}
