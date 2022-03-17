package com.cubo.data.response

import com.cubo.domain.Pokemon

sealed class ResponseCase {

    data class PokemonListResponse(
        var authorized: Boolean = true,
        var pokemonList: List<Pokemon> = arrayListOf()
    ): ResponseCase()

}
