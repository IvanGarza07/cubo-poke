package com.cubo.app.presentation.base.fragment

import androidx.navigation.NavArgs
import com.cubo.app.presentation.base.viewmodel.BaseNavigationViewModel
import org.koin.androidx.scope.ScopeFragment
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf
import kotlin.reflect.KClass

abstract class BaseViewModelFragment<VM : BaseNavigationViewModel> : ScopeFragment() {

    open val arguments: NavArgs? = null

    protected abstract val viewModelClass: KClass<VM>

    protected open val viewModel: VM by lazy {
        getViewModel(clazz = viewModelClass) {
            getParametersOfInject()
        }
    }

    open fun getParametersOfInject() = parametersOf(arguments)

}
