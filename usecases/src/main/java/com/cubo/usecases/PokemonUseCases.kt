package com.cubo.usecases

import com.cubo.data.params.PokemonListParams
import com.cubo.data.repository.PokemonRepository

class PokemonUseCases(private val pokemonRepository: PokemonRepository) {

    suspend fun invokePokemonList(pokemonListParams: PokemonListParams) =
        pokemonRepository.getPokemonList(pokemonListParams)

    suspend fun invokePokemonDetail(pokemonId: String) = pokemonRepository.getPokemonDetail(pokemonId)
}