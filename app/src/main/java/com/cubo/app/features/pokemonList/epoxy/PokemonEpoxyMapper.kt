package com.cubo.app.features.pokemonList.epoxy

import com.cubo.app.presentation.data.models.PokemonModel
import com.cubo.domain.Pokemon

fun Pokemon.toPokemonModel() = PokemonModel(
    id = this.id,
    name = this.name,
    desc = this.description,
    image = this.image
)
