package com.cubo.app.presentation.base.viewmodel

import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.cubo.app.presentation.utils.SingleLiveEvent

abstract class BaseNavigationViewModel : ViewModel() {

    private val navigation: SingleLiveEvent<NavDirections> = SingleLiveEvent()
    private val navigationId: SingleLiveEvent<Int> = SingleLiveEvent()
    private val navigationBackStackWithDirection: SingleLiveEvent<Int> = SingleLiveEvent()
    private val navigationBackStack: SingleLiveEvent<Boolean> = SingleLiveEvent()
    private val finishHost: SingleLiveEvent<Boolean> = SingleLiveEvent()
    private val buttonLoading: SingleLiveEvent<Boolean> = SingleLiveEvent()

    fun getNavigationLiveData() = navigation

    fun getNavigationIdLiveData() = navigationId

    fun getPopBackStackWithDirectionLiveData() = navigationBackStackWithDirection

    fun getPopBackStackLiveData() = navigationBackStack

    fun getFinishHostLiveData() = finishHost

    fun getButtonLoadingLiveData() = buttonLoading

    fun navigateTo(direction: NavDirections) {
        navigation.postValue(direction)
    }

    fun navigateToId(direction: Int) {
        navigationId.postValue(direction)
    }

    fun popBackStackWithDirection(direction: Int) {
        navigationBackStackWithDirection.postValue(direction)
    }

    fun popBackStack() {
        navigationBackStack.postValue(true)
    }

    fun finishHost() {
        finishHost.postValue(true)
    }

    fun initButtonLoading(isLoading: Boolean) {
        buttonLoading.postValue(isLoading)
    }
}
