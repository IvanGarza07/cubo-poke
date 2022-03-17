package com.cubo.app.features.login

import com.cubo.app.R
import com.cubo.app.data.resource.ResourceDataSource
import com.cubo.app.presentation.base.viewmodel.BaseNavigationViewModel
import com.cubo.app.presentation.extensions.launch
import com.cubo.app.presentation.utils.SingleLiveEvent
import com.cubo.data.params.LoginUserParams
import com.cubo.usecases.UserUseCases
import kotlinx.coroutines.CoroutineDispatcher

class LoginViewModel(
    private val dispatcher: CoroutineDispatcher,
    private val resourceDataSource: ResourceDataSource,
    private val userUseCases: UserUseCases
) : BaseNavigationViewModel() {

    private val errorLoginLiveData: SingleLiveEvent<String> = SingleLiveEvent()

    fun errorLoginLiveData() = errorLoginLiveData

    fun loginUser(email: String, password: String) {
        initButtonLoading(true)
        if (email.isEmpty() || password.isEmpty())
            loginFail(R.string.login_error_message_1)
        else {
            launch(dispatcher) {
                val partnerUser = userUseCases.invokeLoginUser(getLoginParams(email, password))
                initButtonLoading(false)
                onFinishedLogin(partnerUser)
            }
        }
    }

    private fun onFinishedLogin(token: String) {
        if (token.isNotEmpty())
            navigateTo(LoginFragmentDirections.actionLoginToList())
        else
            loginFail(R.string.login_error_message_2)
    }

    private fun loginFail(message: Int) {
        initButtonLoading(false)
        errorLoginLiveData.postValue(resourceDataSource.getString(message))
    }

    private fun getLoginParams(email: String, password: String) = LoginUserParams(
        email = email,
        password = password
    )
}