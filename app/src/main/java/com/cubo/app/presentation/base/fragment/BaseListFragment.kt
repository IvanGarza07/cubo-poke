package com.cubo.app.presentation.base.fragment

import androidx.databinding.ViewDataBinding
import com.cubo.app.BR
import com.cubo.app.presentation.base.epoxy.BaseEpoxyController
import com.cubo.app.presentation.base.viewmodel.BaseListViewModel
import com.cubo.app.presentation.data.interfaces.ListModel
import com.cubo.app.presentation.extensions.liveDataObserver

abstract class BaseListFragment<VM : BaseListViewModel, LB : ViewDataBinding> : BaseListNavigationFragment<VM, LB>() {

    abstract val listController: BaseEpoxyController

    override fun initBindingVariables() {
        super.initBindingVariables()
        binding.setVariable(BR.recyclerController, listController)
    }

    override fun initObserverList(isSnapshot: Boolean) {
        super.initObserverList(isSnapshot)
        liveDataObserver(viewModel.getListLiveData()) { models ->
            setList(models)
        }
    }

    override fun showSkeleton() {
        super.showSkeleton()
        setList(viewModel.getSkeletons())
    }

    open fun setList(list: List<ListModel>) {
        listController.setData(list)
    }
}