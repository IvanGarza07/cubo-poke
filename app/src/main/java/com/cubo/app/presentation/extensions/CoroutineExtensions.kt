package com.cubo.app.presentation.extensions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun <T> CoroutineScope.collectFlow(flow: Flow<T>, body: suspend (T) -> Unit) {
    flow.onEach { body(it) }.launchIn(this)
}

fun ViewModel.launch(dispatcher: CoroutineDispatcher, action: suspend () -> Unit) {
    viewModelScope.launchSuspend(dispatcher, action)
}

fun CoroutineScope.launchSuspend(
    dispatcher: CoroutineDispatcher,
    action: suspend () -> Unit
) {
    this.launch {
        withContext((dispatcher)) {
            try {
                action.invoke()
            } catch (throwable: Throwable) {
                throwable.chooseViewModelError()
            }
        }
    }
}