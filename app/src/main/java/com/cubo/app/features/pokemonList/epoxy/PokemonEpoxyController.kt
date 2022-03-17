package com.cubo.app.features.pokemonList.epoxy

import com.cubo.app.pokemon
import com.cubo.app.presentation.base.epoxy.BaseEpoxyController
import com.cubo.app.presentation.base.epoxy.BaseEpoxyEventListener
import com.cubo.app.presentation.data.interfaces.ListModel
import com.cubo.app.presentation.data.models.PokemonModel

class PokemonEpoxyController(override val eventListener: BaseEpoxyEventListener) :
    BaseEpoxyController() {

    override fun buildModel(id: String, model: ListModel) {
        when (model) {
            is PokemonModel -> {
                    pokemon {
                        id(id)
                        model(model)
                        pokemonClickListener { model, _, _, _ ->
                            this@PokemonEpoxyController.launchEvent(
                                PokemonEpoxyEvent.OpenPokemonDetail(model.model().id)
                            )
                        }
                    }
            }
        }
    }
}
