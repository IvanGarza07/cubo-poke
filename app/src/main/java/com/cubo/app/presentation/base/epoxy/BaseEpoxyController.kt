package com.cubo.app.presentation.base.epoxy


import com.airbnb.epoxy.TypedEpoxyController
import com.cubo.app.dividerLine
import com.cubo.app.presentation.data.interfaces.ListModel
import com.cubo.app.presentation.data.models.DividerLineModel
import com.cubo.app.presentation.data.models.SkeletonModel
import com.cubo.app.presentation.extensions.addSkeletonModels

abstract class BaseEpoxyController : TypedEpoxyController<List<ListModel>>() {

    abstract val eventListener: BaseEpoxyEventListener

    abstract fun buildModel(id: String, model: ListModel)

    override fun buildModels(data: List<ListModel>?) {
        addSkeletonModels(data?.filterIsInstance<SkeletonModel>())
        mappingCommonModels(data)
    }

    override fun setFilterDuplicates(filterDuplicates: Boolean) {
        super.setFilterDuplicates(true)
    }

    fun <T : BaseEpoxyClickEvent> launchEvent(event: T) = eventListener.event.invoke(event)

    private fun mappingCommonModels(data: List<ListModel>?) {
        data?.mapIndexed { index, model ->
            val id = model.id.ifEmpty { index.toString() }
            when (model) {
                is DividerLineModel -> dividerLine {
                    id(id)
                }
                else -> buildModel(id, model)
            }
        }
    }
}
