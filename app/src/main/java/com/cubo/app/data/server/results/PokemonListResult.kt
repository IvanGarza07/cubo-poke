package com.cubo.app.data.server.results

import com.google.gson.annotations.SerializedName

data class PokemonListResult(
    var data: List<PokemonResult> = arrayListOf()
)

data class PokemonResult(
    var id: String = "",
    var name: String = "",
    var description: String = "",
    @SerializedName("image_url") var image: String = "",
    var date: String = "",
    var released: String = "",
    var type: String = "",
    var category: String = "",
)
