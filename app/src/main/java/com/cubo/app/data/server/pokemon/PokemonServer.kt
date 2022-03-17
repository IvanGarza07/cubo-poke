package com.cubo.app.data.server.pokemon

import com.cubo.app.data.server.utils.ServerUtils
import com.cubo.app.data.server.utils.toPokemonList
import com.cubo.data.params.PokemonListParams
import com.cubo.data.response.ResponseCase
import com.cubo.data.source.PokemonDataSource

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

    override suspend fun getPokemonDetail(pokemonId: String, token: String): ResponseCase.PokemonDetailResponse {
        val response = pokemonService.getPokemonDetail(
            ServerUtils.getApiServiceHeaders(true, token),
            pokemonId
        )
        val responseCase = ResponseCase.PokemonDetailResponse()
        when(response.code()) {
            200 -> {
                response.body()?.let {
                    responseCase.pokemon = it.toPokemonList()[0]
                }
            }
            401 -> responseCase.authorized = false
        }
        return responseCase
    }

}