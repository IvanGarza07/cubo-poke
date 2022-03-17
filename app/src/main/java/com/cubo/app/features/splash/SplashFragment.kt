package com.cubo.app.features.splash

import com.cubo.app.R
import com.cubo.app.databinding.FragmentSplashBinding
import com.cubo.app.presentation.base.fragment.BaseFragment
import kotlin.reflect.KClass


class SplashFragment : BaseFragment<SplashViewModel, FragmentSplashBinding>() {

    override val viewLayout: Int = R.layout.fragment_splash

    override val viewModelClass: KClass<SplashViewModel> = SplashViewModel::class

    override fun initStatusBarColor(updateColor: Boolean, color: Int) {
        super.initStatusBarColor(true, R.color.purple_500)
    }

    override fun initNavBarColor(updateColor: Boolean, color: Int) {
        super.initNavBarColor(true, R.color.white)
    }
}