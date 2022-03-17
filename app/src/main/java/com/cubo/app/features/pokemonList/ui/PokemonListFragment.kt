package com.cubo.app.features.pokemonList.ui

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cubo.app.R
import com.cubo.app.databinding.FragmentPokemonListBinding
import com.cubo.app.features.dialogs.ConfirmActionDialog
import com.cubo.app.features.pokemonList.epoxy.PokemonEpoxyController
import com.cubo.app.presentation.base.fragment.BaseListFragment
import com.cubo.app.presentation.data.enums.EventEnum
import com.cubo.app.presentation.data.models.AppEvent
import com.cubo.app.presentation.extensions.epoxyInject
import com.cubo.app.presentation.extensions.initBackPressedToFinishHost
import com.cubo.app.presentation.extensions.liveDataObserver
import com.cubo.app.presentation.extensions.showSnackBar
import com.cubo.app.presentation.utils.EventUtils
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import kotlin.reflect.KClass


class PokemonListFragment : BaseListFragment<PokemonListViewModel, FragmentPokemonListBinding>() {

    private var isLoading = false

    private val confirmActionDialog by inject<ConfirmActionDialog> {
        parametersOf(requireContext())
    }

    override val viewLayout = R.layout.fragment_pokemon_list

    override val viewModelClass: KClass<PokemonListViewModel> = PokemonListViewModel::class

    override val listController by epoxyInject<PokemonEpoxyController>(lazyViewModel)

    override fun initStatusBarColor(updateColor: Boolean, color: Int) {
        super.initStatusBarColor(true, R.color.purple_500)
    }

    override fun initNavBarColor(updateColor: Boolean, color: Int) {
        super.initNavBarColor(true, R.color.white)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.initPokemonList()
    }

    override fun initObservers() {
        super.initObservers()
        liveDataObserver(viewModel.unauthorizedLiveData()) {
            showCloseSessionDialog()
        }
        liveDataObserver(viewModel.pageLoadedLiveData()) {
            isLoading = false
        }
        observerAppEvents()
        requireActivity().initBackPressedToFinishHost(viewLifecycleOwner)
    }

    override fun intUI() {
        super.intUI()
        initRecyclerListener()
    }

    private fun observerAppEvents() {
        EventUtils.listen(this) {
            when (it.case) {
                EventEnum.VIEW_MODEL_ERROR -> {
                    val message = if (it.value is String) it.value else getString(it.value as Int)
                    binding.linearPokemon.showSnackBar(message, true)
                }
                else -> {
                    //Nothing to do
                }
            }
            EventUtils.publish(AppEvent(EventEnum.CLEAR_EVENT))
        }
    }

    private fun initRecyclerListener() {
        binding.epoxyRecyclerPokemon.recyclerView.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
                if (!isLoading &&
                    linearLayoutManager != null &&
                    linearLayoutManager.findLastCompletelyVisibleItemPosition() == viewModel.models.size - 1) {
                    viewModel.loadNextPage()
                    isLoading = true
                }
            }
        })
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