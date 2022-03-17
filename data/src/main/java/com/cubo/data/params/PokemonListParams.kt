package com.cubo.data.params

data class PokemonListParams(
    var page: Int = 0,
    var perPage: Int = 0,
    var token: String = ""
)
