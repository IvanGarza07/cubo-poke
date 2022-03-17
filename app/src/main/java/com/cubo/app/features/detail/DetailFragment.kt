package com.cubo.app.features.detail

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.cubo.app.R
import com.cubo.app.databinding.FragmentDetailBinding
import com.cubo.app.features.dialogs.ConfirmActionDialog
import com.cubo.app.features.pokemonList.ui.PokemonListFragmentDirections
import com.cubo.app.presentation.base.fragment.BaseFragment
import com.cubo.app.presentation.data.enums.EventEnum
import com.cubo.app.presentation.data.models.AppEvent
import com.cubo.app.presentation.extensions.liveDataObserver
import com.cubo.app.presentation.extensions.makeGone
import com.cubo.app.presentation.extensions.makeVisible
import com.cubo.app.presentation.extensions.showSnackBar
import com.cubo.app.presentation.utils.EventUtils
import com.cubo.domain.Pokemon
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import kotlin.reflect.KClass


class DetailFragment : BaseFragment<DetailViewModel, FragmentDetailBinding>() {

    private val confirmActionDialog by inject<ConfirmActionDialog> {
        parametersOf(requireContext())
    }

    override val viewLayout = R.layout.fragment_detail

    override val viewModelClass: KClass<DetailViewModel> = DetailViewModel::class

    override val arguments by navArgs<DetailFragmentArgs>()

    override fun initStatusBarColor(updateColor: Boolean, color: Int) {
        super.initStatusBarColor(true, R.color.purple_500)
    }

    override fun initNavBarColor(updateColor: Boolean, color: Int) {
        super.initNavBarColor(true, R.color.white)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getPokemonDetail()
    }

    override fun initBindingVariables() {
        super.initBindingVariables()
        binding.pokemon = Pokemon()
    }

    override fun initObservers() {
        super.initObservers()
        liveDataObserver(viewModel.pokemonLiveData()) {
            binding.pokemon = it
            binding.progressBarPokemon.makeGone()
            binding.linearLayoutDetail.makeVisible()
        }
        liveDataObserver(viewModel.unauthorizedLiveData()) {
            showCloseSessionDialog()
        }
        observerAppEvents()
    }

    private fun observerAppEvents() {
        EventUtils.listen(this) {
            when (it.case) {
                EventEnum.VIEW_MODEL_ERROR -> {
                    val message = if (it.value is String) it.value else getString(it.value as Int)
                    showLoginError(message)
                }
                else -> {
                    //Nothing to do
                }
            }
            EventUtils.publish(AppEvent(EventEnum.CLEAR_EVENT))
        }
    }

    private fun showLoginError(message: String) {
        binding.linearLayoutDetail.showSnackBar(message, true)
    }

    private fun showCloseSessionDialog() {
        confirmActionDialog.setOnCancelButtonClickListener {
            confirmActionDialog.dismiss()
        }
        confirmActionDialog.setOnConfirmButtonClickListener {
            viewModel.navigateTo(PokemonListFragmentDirections.actionListToLogin())
            confirmActionDialog.dismiss()
        }
        confirmActionDialog.startDialog()
        confirmActionDialog.showDialog()
    }
}