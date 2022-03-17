/*
 *  Create by Ivan Garza on 1/28/22, 8:33 PM.
 *
 *  Copyright (c) year Ivan Garza.
 *  All rights reserved.
 */

package com.cubo.app.features.pokemonList.ui

import com.cubo.app.R
import com.cubo.app.features.pokemonList.epoxy.toPokemonModel
import com.cubo.app.presentation.base.epoxy.BaseEpoxyClickEvent
import com.cubo.app.presentation.base.viewmodel.BaseListViewModel
import com.cubo.app.presentation.data.interfaces.ListModel
import com.cubo.app.presentation.data.models.SkeletonModel
import com.cubo.app.presentation.extensions.launch
import com.cubo.app.presentation.utils.SingleLiveEvent
import com.cubo.data.params.PokemonListParams
import com.cubo.data.response.ResponseCase
import com.cubo.usecases.PokemonUseCases
import kotlinx.coroutines.CoroutineDispatcher

class PokemonListViewModel(
    private val dispatcher: CoroutineDispatcher,
    private val pokemonUseCases: PokemonUseCases
) : BaseListViewModel() {

    private var page = 1

    private val unauthorizedLiveData: SingleLiveEvent<Boolean> = SingleLiveEvent()

    override val models = arrayListOf<ListModel>()

    override val event: (event: BaseEpoxyClickEvent) -> Unit = { event ->
        when (event) {

        }
    }

    override fun getSkeletons(): List<ListModel> {
        models.clear()
        fun getOrdersListSkeletonModel(): List<ListModel> {
            val models = arrayListOf<ListModel>()
            for (i in 0..4) {
                models.add(SkeletonModel(viewLayout = R.layout.epoxy_item_skeleton_pokemon))
            }
            return models
        }
        return arrayListOf<ListModel>().apply {
            addAll(getOrdersListSkeletonModel())
        }
    }

    fun unauthorizedLiveData() = unauthorizedLiveData

    fun initPokemonList() {
        models.clear()
        loadPokemonList()
    }

    fun loadNextPage() {
        page++
        loadPokemonList()
    }

    private fun loadPokemonList() = launch(dispatcher) {
        val result = pokemonUseCases.invokePokemonList(getPokemonListParams())
        val pokemonResult = result as ResponseCase.PokemonListResponse
        if (pokemonResult.authorized) {
            pokemonResult.pokemonList.forEach {
                models.add(it.toPokemonModel())
            }
            initList(models)
        } else
            unauthorizedLiveData.postValue(true)
    }

    private fun getPokemonListParams() = PokemonListParams(
        page = page,
        perPage = 20
    )
}