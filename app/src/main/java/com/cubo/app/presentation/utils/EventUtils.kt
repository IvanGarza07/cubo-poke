package com.cubo.app.presentation.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.cubo.app.presentation.data.models.AppEvent

object EventUtils {
    private val EVENT_BUS by lazy {
        MutableLiveData<AppEvent<Any>>()
    }

    fun publish(event: AppEvent<Any>) {
        EVENT_BUS.postValue(event)
    }

    fun listen(owner: LifecycleOwner, action: (AppEvent<*>) -> Unit) {
        EVENT_BUS.observe(owner) { action.invoke(it) }
    }
}