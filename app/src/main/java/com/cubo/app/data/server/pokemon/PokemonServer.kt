package com.cubo.app.data.server.pokemon

import com.cubo.app.data.server.utils.ServerUtils
import com.cubo.app.data.server.utils.toPokemonList
import com.cubo.data.params.PokemonListParams
import com.cubo.data.response.ResponseCase
import com.cubo.data.source.PokemonDataSource
import com.cubo.domain.Pokemon

class PokemonServer(private val pokemonService: PokemonService): PokemonDataSource {

    override suspend fun getPokemonList(pokemonListParams: PokemonListParams): ResponseCase.PokemonListResponse {
        val response = pokemonService.pokemonList(
            ServerUtils.getApiServiceHeaders(true, pokemonListParams.token),
            pokemonListParams.page,
            pokemonListParams.perPage
        )
        val responseCase = ResponseCase.PokemonListResponse()
        when(response.code()) {
            200 -> {
                response.body()?.let {
                    responseCase.pokemonList = it.toPokemonList()
                }
            }
            401 -> responseCase.authorized = false
        }
        return responseCase
    }

}