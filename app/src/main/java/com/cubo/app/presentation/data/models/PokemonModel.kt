package com.cubo.app.presentation.data.models

import com.cubo.app.presentation.data.interfaces.ListModel

data class PokemonModel(
    override val id: String = "",
    var name: String = "",
    var desc: String = "",
    var image: String = ""
): ListModel
