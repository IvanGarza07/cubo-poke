package com.cubo.app.presentation.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.cubo.app.BR
import com.cubo.app.R
import com.cubo.app.presentation.base.viewmodel.BaseNavigationViewModel
import com.cubo.app.presentation.extensions.showSystemUI
import com.cubo.app.presentation.extensions.updateNavBarColor
import com.cubo.app.presentation.extensions.updateStatusBarColor

abstract class BaseFragment<VM : BaseNavigationViewModel, LB : ViewDataBinding> :
    BaseNavigationFragment<VM>() {

    open val isSaveViewLayout: Boolean = false
    protected abstract val viewLayout: Int

    private var viewFragmentBinding: LB? = null
    val binding get() = viewFragmentBinding!!

    private var isLoadedEventFragment = false
    val isLoadFragment get() = isLoadedEventFragment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (isSaveViewLayout) {
            if (viewFragmentBinding == null) {
                viewFragmentBinding = DataBindingUtil
                    .inflate(inflater, viewLayout, container, false)
            }
        } else {
            viewFragmentBinding = DataBindingUtil
                .inflate(inflater, viewLayout, container, false)
        }
        initBindingVariables()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBaseFunctions()
        if (isSaveViewLayout) {
            isLoadedEventFragment = true
        }
    }

    override fun initObservers() {
        super.initObservers()
        initAdditionalObservers()
        initRecyclerObserver()
        initFragmentObservers()
    }

    open fun initBindingVariables() {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.setVariable(BR.viewModel, viewModel)
    }

    open fun initBaseFunctions() {
        initStatusBarColor()
        initNavBarColor()
        intUI()
        initObservers()
    }

    open fun initStatusBarColor(updateColor: Boolean = true, color: Int = R.color.white) {
        if (updateColor) {
            with(requireActivity()) {
                showSystemUI()
                updateStatusBarColor(color)
            }
        }
    }

    open fun initNavBarColor(updateColor: Boolean = true, color: Int = R.color.white) {
        if (updateColor) {
            with(requireActivity()) {
                updateNavBarColor(color)
            }
        }
    }

    open fun intUI() {
        //Nothing to do
    }
}
