package com.cubo.app.presentation.base.fragment

import androidx.databinding.ViewDataBinding
import com.cubo.app.presentation.base.viewmodel.BaseListViewModel
import com.cubo.app.presentation.extensions.liveDataObserver

abstract class BaseListNavigationFragment<VM : BaseListViewModel, LB : ViewDataBinding> : BaseFragment<VM, LB>() {

    protected val lazyViewModel = lazy { viewModel }

    private var isShowPreviousSkeleton: Boolean = false

    override fun initBaseFunctions() {
        super.initBaseFunctions()
        if (!isShowPreviousSkeleton) showSkeleton()
    }

    override fun initRecyclerObserver() {
        initObserverList()
    }

    open fun initObserverList(isSnapshot: Boolean = false) {
        liveDataObserver(viewModel.callSkeletonLiveData()) {
            isShowPreviousSkeleton = false
            showSkeleton()
        }
    }

    open fun showSkeleton() {
        isShowPreviousSkeleton = true
    }
}
