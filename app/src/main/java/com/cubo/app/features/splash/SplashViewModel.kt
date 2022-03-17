package com.cubo.app.features.splash

import com.cubo.app.presentation.base.viewmodel.BaseNavigationViewModel
import com.cubo.app.presentation.extensions.launch
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

class SplashViewModel(private val dispatcher: CoroutineDispatcher) : BaseNavigationViewModel() {

    init {
        chooseNavigateTo()
    }

    private fun chooseNavigateTo() = launch(dispatcher) {
        delay(TimeUnit.SECONDS.toMillis(2))
        navigateTo(SplashFragmentDirections.actionSplashToLogin())
    }

}