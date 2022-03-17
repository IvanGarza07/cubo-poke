package com.cubo.data.source

import com.cubo.data.params.PokemonListParams
import com.cubo.data.response.ResponseCase
import com.cubo.domain.Pokemon

interface PokemonDataSource {

    suspend fun getPokemonList(pokemonListParams: PokemonListParams): ResponseCase.PokemonListResponse

}