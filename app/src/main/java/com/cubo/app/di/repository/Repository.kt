/*
 *  Create by Ivan Garza on 1/10/22, 2:00 PM.
 *
 *  Copyright (c) year Ivan Garza.
 *  All rights reserved.
 */

package com.cubo.app.di.repository

import android.content.Context
import androidx.datastore.core.DataStore
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.cubo.app.AppPreference
import com.cubo.app.BuildConfig
import com.cubo.app.CuboApplication
import com.cubo.app.features.dialogs.ConfirmActionDialog
import com.cubo.app.presentation.extensions.logV
import com.cubo.app.presentation.utils.DispatcherProvider
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object Repository {

    fun provideOkhttpClient(context: Context): OkHttpClient.Builder {
        try {
            val builder = OkHttpClient.Builder()
                .connectTimeout(90, TimeUnit.SECONDS)
                .readTimeout(90, TimeUnit.SECONDS)
                .writeTimeout(90, TimeUnit.SECONDS)
                .retryOnConnectionFailure(false)
            setDebugInterceptors(context, builder)
            return builder
        } catch (e: Exception) {
            "error en interceptor ${e.message}".logV()
            throw RuntimeException(e)
        }
    }

    internal fun provideDispatcherProvider(): DispatcherProvider = object : DispatcherProvider {
        override val default: CoroutineDispatcher = Dispatchers.Default
        override val io: CoroutineDispatcher = Dispatchers.IO
        override val main: CoroutineDispatcher = Dispatchers.Main
        override val unconfined: CoroutineDispatcher = Dispatchers.Unconfined
    }

    internal fun provideDataStore(): DataStore<AppPreference> {
        return CuboApplication.dataStore
    }

    internal fun provideConfirmDialog(
        context: Context
    ): ConfirmActionDialog {
        return ConfirmActionDialog(context)
    }

    inline fun <reified T> provideRetrofit(
        context: Context,
        url: String
    ): T {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(url)
            .client(provideOkhttpClient(context).build())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
        return retrofit.create(T::class.java)
    }

    internal fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        return gsonBuilder.create()
    }

    private fun setDebugInterceptors(context: Context, builder: OkHttpClient.Builder) {
        if (BuildConfig.DEBUG) {
            val interceptor = ChuckerInterceptor.Builder(context)
                .collector(ChuckerCollector(context))
                .maxContentLength(250000L)
                .redactHeaders(emptySet())
                .alwaysReadResponseBody(false)
                .build()
            builder.addInterceptor(OkHttpProfilerInterceptor())
            builder.addInterceptor(interceptor)
        }
    }
}
