package com.cubo.app.features.detail

import com.cubo.app.R
import com.cubo.app.data.resource.ResourceDataSource
import com.cubo.app.features.pokemonList.epoxy.toPokemonModel
import com.cubo.app.presentation.base.viewmodel.BaseNavigationViewModel
import com.cubo.app.presentation.extensions.launch
import com.cubo.app.presentation.utils.SingleLiveEvent
import com.cubo.data.response.ResponseCase
import com.cubo.domain.Pokemon
import com.cubo.usecases.PokemonUseCases
import kotlinx.coroutines.CoroutineDispatcher

class DetailViewModel(
    private val arguments: DetailFragmentArgs,
    private val dispatcher: CoroutineDispatcher,
    private val resourceDataSource: ResourceDataSource,
    private val pokemonUseCases: PokemonUseCases
) : BaseNavigationViewModel() {

    private val pokemonLiveData: SingleLiveEvent<Pokemon> = SingleLiveEvent()

    private val unauthorizedLiveData: SingleLiveEvent<Boolean> = SingleLiveEvent()

    fun pokemonLiveData() = pokemonLiveData

    fun unauthorizedLiveData() = unauthorizedLiveData

    fun getPokemonDetail() = launch(dispatcher) {
        val result = pokemonUseCases.invokePokemonDetail(arguments.pokemonId)
        val pokemonResult = result as ResponseCase.PokemonDetailResponse
        if (pokemonResult.authorized) {
            val detail = pokemonResult.pokemon
            detail.type = resourceDataSource.getString(R.string.detail_type_value, pokemonResult.pokemon.type)
            detail.category = resourceDataSource.getString(R.string.detail_category_value, pokemonResult.pokemon.category)
            pokemonLiveData.postValue(pokemonResult.pokemon)
        } else
            unauthorizedLiveData.postValue(true)
    }

}