package com.cubo.data.repository

import com.cubo.data.params.PokemonListParams
import com.cubo.data.response.ResponseCase
import com.cubo.data.source.PokemonDataSource
import com.cubo.data.source.PreferenceDataSource
import com.cubo.domain.Pokemon
import kotlinx.coroutines.flow.first

class PokemonRepository(
    private val pokemonDataSource: PokemonDataSource,
    private val preferenceDataSource: PreferenceDataSource
    ) {

    suspend fun getPokemonList(pokemonListParams: PokemonListParams): ResponseCase {
        val token = preferenceDataSource.getToken().first()
        pokemonListParams.token = token
        return pokemonDataSource.getPokemonList(pokemonListParams)
    }

    suspend fun getPokemonDetail(pokemonId: String): ResponseCase {
        val token = preferenceDataSource.getToken().first()
        return pokemonDataSource.getPokemonDetail(pokemonId, token)
    }

}