package com.cubo.app.presentation.data.enums

enum class EventEnum {
    INCOMING_ORDER,
    RELOAD_ORDERS,
    SHOW_ORDER_PREPARATION_DIALOG,
    SHOW_ORDER_READY_DIALOG,
    SHOW_ORDER_DISPATCH_DIALOG,
    OPEN_ORDER_DETAIL,
    ON_REJECT_ORDER,
    ON_CALL_REQUEST_SENT,
    VIEW_MODEL_ERROR,
    CANCELED_ORDER,
    CLEAR_EVENT
}