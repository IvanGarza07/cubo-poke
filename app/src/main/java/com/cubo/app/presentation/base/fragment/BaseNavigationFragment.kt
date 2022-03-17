package com.cubo.app.presentation.base.fragment

import android.view.View
import androidx.navigation.fragment.findNavController
import com.cubo.app.presentation.base.viewmodel.BaseNavigationViewModel
import com.cubo.app.presentation.extensions.hideKeyboard
import com.cubo.app.presentation.extensions.liveDataObserver

abstract class BaseNavigationFragment<VM : BaseNavigationViewModel> : BaseViewModelFragment<VM>(),
    BaseFragmentObserver {

    protected val onClickPopBackStack: (View) -> Unit = {
        viewModel.popBackStack()
    }

    override fun initObservers() {
        setNavigationObserver()
    }

    private fun setNavigationObserver() {
        view?.hideKeyboard()

        liveDataObserver(viewModel.getNavigationLiveData()) { direction ->
            findNavController().navigate(direction)
        }

        liveDataObserver(viewModel.getPopBackStackWithDirectionLiveData()) { direction ->
            findNavController().popBackStack(direction, true)
        }

        liveDataObserver(viewModel.getPopBackStackLiveData()) {
            popBackStack()
        }

        liveDataObserver(viewModel.getFinishHostLiveData()) {
            requireActivity().finish()
        }
    }

    open fun popBackStack() {
        setFragmentNavigationResult()
        findNavController().popBackStack()
    }

    open fun setFragmentNavigationResult() {
        //Nothing to do
    }
}
