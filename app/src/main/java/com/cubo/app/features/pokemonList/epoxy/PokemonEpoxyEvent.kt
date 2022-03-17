package com.cubo.app.features.pokemonList.epoxy

import com.cubo.app.presentation.base.epoxy.BaseEpoxyClickEvent

sealed class PokemonEpoxyEvent : BaseEpoxyClickEvent {

    data class OpenPokemonDetail(val pokemonId: String) : PokemonEpoxyEvent()

}
