package com.cubo.app.presentation.data.models

import com.cubo.app.presentation.data.enums.EventEnum

class AppEvent<T>(val case: EventEnum, val value: T? = null)