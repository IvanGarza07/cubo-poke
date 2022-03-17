package com.cubo.app.presentation.extensions

import android.content.ComponentCallbacks
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.carousel
import com.cubo.app.presentation.base.epoxy.BaseEpoxyEventListener
import com.cubo.app.presentation.base.epoxy.BaseSkeletonModel
import com.cubo.app.presentation.base.epoxy.BaseSkeletonModel_
import com.cubo.app.presentation.base.epoxy.baseSkeleton
import com.cubo.app.presentation.bindingAdapters.setBouncyEffect
import com.cubo.app.presentation.data.interfaces.CarouselModel
import com.cubo.app.presentation.data.models.CarouselSettingModel
import com.cubo.app.presentation.data.models.CarouselSkeletonModel
import com.cubo.app.presentation.data.models.SkeletonModel
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

inline fun <reified T : EpoxyController> ComponentCallbacks.epoxyInject(
    listener: Lazy<BaseEpoxyEventListener>
) = inject<T> { parametersOf(listener.value) }

fun EpoxyController.addSkeletonModels(models: List<SkeletonModel>?) {
    models?.mapIndexed { index, model ->
        if (model.isVerticalOrientation) {
            for (i in 1..model.itemRepeat) {
                baseSkeleton {
                    id("vertical_skeleton_${index + i}")
                    viewLayout(model.viewLayout)
                    if (model.isNeedSpanSizeOverride) {
                        spanSizeOverride { totalSpanCount, _, _ ->
                            return@spanSizeOverride totalSpanCount
                        }
                    }
                }
            }
        } else {
            val horizontalSkeletons = mutableListOf<BaseSkeletonModel>()
            for (i in 1..model.itemRepeat) {
                horizontalSkeletons.add(
                    BaseSkeletonModel_()
                        .id("horizontal_skeleton_${index + i}")
                        .viewLayout(model.viewLayout)
                )
            }
            val carouselModel = CarouselSkeletonModel(
                id = "carousel_skeleton_${index}",
                startEndPadding = model.startEndPadding,
                itemSpacing = model.itemSpacing
            )
            getCarousel(carouselModel, horizontalSkeletons)
        }
    }
}

fun EpoxyController.getCarousel(
    model: CarouselModel,
    models: List<EpoxyModel<*>>,
    setting: CarouselSettingModel = CarouselSettingModel()
) {
    carousel {
        id(model.id)
        padding(
            Carousel
                .Padding(
                    model.startEndPadding,
                    model.topBottomPadding,
                    model.startEndPadding,
                    model.topBottomPadding,
                    model.itemSpacing
                )
        )
        Carousel.setDefaultGlobalSnapHelperFactory(null)
        models(models)
        onBind { _, view, _ ->
            view.apply {
                if (setting.isColor) setBackgroundColor(
                    ContextCompat.getColor(
                        view.context,
                        setting.color
                    )
                )
                if (setting.isBouncy) {
                    setBouncyEffect(RecyclerView.HORIZONTAL)
                } else {
                    edgeEffectFactory = RecyclerView.EdgeEffectFactory()
                }
                layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            }
        }
    }
}
