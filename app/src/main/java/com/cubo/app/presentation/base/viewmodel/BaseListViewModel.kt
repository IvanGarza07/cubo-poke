package com.cubo.app.presentation.base.viewmodel

import com.cubo.app.presentation.base.epoxy.BaseEpoxyEventListener
import com.cubo.app.presentation.data.interfaces.ListModel
import com.cubo.app.presentation.data.models.SkeletonModel
import com.cubo.app.presentation.utils.SingleLiveEvent


abstract class BaseListViewModel: BaseNavigationViewModel(), BaseEpoxyEventListener {

    abstract val models: List<ListModel>

    private val listLiveData: SingleLiveEvent<List<ListModel>> = SingleLiveEvent()

    private val skeletonLiveData = SingleLiveEvent<Boolean>()

    open fun getSkeletons(): List<ListModel> = emptyList()

    fun getListLiveData() = listLiveData

    fun callSkeletonLiveData() = skeletonLiveData

    fun initList(list: List<ListModel>) {
        listLiveData.postValue(list)
    }

    fun initSkeleton(setSkeleton: Boolean) {
        skeletonLiveData.postValue(setSkeleton)
    }

    protected fun getHorizontalSkeletonModel(
        viewLayout: Int,
        repeat: Int,
        startEndPadding: Int = 0,
        itemSpacing: Int = 0
    ) = SkeletonModel(
        viewLayout = viewLayout,
        itemRepeat = repeat,
        isVerticalOrientation = false,
        startEndPadding = startEndPadding,
        itemSpacing = itemSpacing
    )
}