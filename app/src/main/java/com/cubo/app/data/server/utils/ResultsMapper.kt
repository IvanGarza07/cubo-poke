/*
 *  Create by Ivan Garza on 1/10/22, 4:49 PM.
 *
 *  Copyright (c) year Ivan Garza.
 *  All rights reserved.
 */

package com.cubo.app.data.server.utils

import com.cubo.app.data.server.results.PokemonListResult
import com.cubo.app.data.server.results.PokemonResult
import com.cubo.domain.Pokemon

fun PokemonListResult.toPokemonList(): List<Pokemon> {
    val pokemonList = arrayListOf<Pokemon>()
    this.data.forEach {
        pokemonList.add(it.toPokemon())
    }
    return pokemonList
}

private fun PokemonResult.toPokemon() = Pokemon(
    id = this.id,
    name = this.name,
    description = this.description,
    image = this.image,
    date = this.date,
    type = this.type,
    category = this.category,
)