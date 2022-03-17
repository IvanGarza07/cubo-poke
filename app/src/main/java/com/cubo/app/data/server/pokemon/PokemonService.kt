package com.cubo.app.data.server.pokemon

import com.cubo.app.data.server.results.PokemonListResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonService {

    @GET("pokemons?")
    suspend fun pokemonList(
        @HeaderMap headers: Map<String, String>,
        @Query("page") location: Int,
        @Query("per_page") status: Int
    ): Response<PokemonListResult>

    @GET("pokemons/{pokemonId}")
    suspend fun getPokemonDetail(
        @HeaderMap headers: Map<String, String>,
        @Path("pokemonId") pokemonId: String
    ): Response<PokemonListResult>
}