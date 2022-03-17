/*
 *  Create by Ivan Garza on 1/10/22, 3:07 PM.
 *
 *  Copyright (c) year Ivan Garza.
 *  All rights reserved.
 */

package com.cubo.app.di.modules

import android.app.Application
import android.content.Context
import com.cubo.app.BuildConfig
import com.cubo.app.data.preferences.PreferenceStore
import com.cubo.app.data.resource.ResourceDataSource
import com.cubo.app.data.resource.ResourceManager
import com.cubo.app.data.server.pokemon.PokemonServer
import com.cubo.app.data.server.pokemon.PokemonService
import com.cubo.app.data.server.user.UserServer
import com.cubo.app.data.server.user.UserService
import com.cubo.app.di.repository.Repository
import com.cubo.app.features.login.LoginFragment
import com.cubo.app.features.login.LoginViewModel
import com.cubo.app.features.pokemonList.ui.PokemonListFragment
import com.cubo.app.features.pokemonList.ui.PokemonListViewModel
import com.cubo.app.features.pokemonList.epoxy.PokemonEpoxyController
import com.cubo.app.features.splash.SplashFragment
import com.cubo.app.features.splash.SplashViewModel
import com.cubo.app.presentation.base.epoxy.BaseEpoxyEventListener
import com.cubo.app.presentation.utils.AppKeys
import com.cubo.app.presentation.utils.GlideService
import com.cubo.data.repository.PokemonRepository
import com.cubo.data.repository.UserRepository
import com.cubo.data.source.PokemonDataSource
import com.cubo.data.source.PreferenceDataSource
import com.cubo.data.source.UserDataSource
import com.cubo.usecases.PokemonUseCases
import com.cubo.usecases.UserUseCases
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module

fun Application.initDI() {
    startKoin {
        androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
        androidContext(this@initDI)
        modules(listOf(utilsModule, appModule, dataModule, scopesModule))
    }
}

private val utilsModule = module {
    single<ResourceDataSource> { ResourceManager(androidContext()) }
    single { Repository.provideGson() }
    single { GlideService(androidContext()) }
    factory { (context: Context) -> Repository.provideConfirmDialog(context) }
}

private val appModule = module {
    single { Repository.provideDispatcherProvider().io }
    single { Repository.provideDataStore() }
    single { Repository.provideRetrofit<UserService>(androidContext(), AppKeys.domain()) }
    single { Repository.provideRetrofit<PokemonService>(androidContext(), AppKeys.domain()) }
    single<PreferenceDataSource> { PreferenceStore(get()) }
    single<UserDataSource> { UserServer(get()) }
    single<PokemonDataSource> { PokemonServer(get()) }
}

private val dataModule = module {
    single { UserRepository(get(), get()) }
    single { PokemonRepository(get(), get()) }
}

private val scopesModule = module {
    scope<SplashFragment> {
        viewModel { SplashViewModel(get()) }
    }
    scope<LoginFragment> {
        viewModel { LoginViewModel(get(), get(), get()) }
        scoped { UserUseCases(get()) }
    }
    scope<PokemonListFragment> {
        viewModel { PokemonListViewModel(get(), get()) }
        scoped { PokemonUseCases(get()) }
        factory { (baseEpoxyEventListener: BaseEpoxyEventListener) ->
            PokemonEpoxyController(
                baseEpoxyEventListener
            )
        }
    }
}