/*
 *  Create by Ivan Garza on 12/16/21, 4:22 PM.
 *
 *  Copyright (c) year Ivan Garza.
 *  All rights reserved.
 */

package com.cubo.app

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.multidex.MultiDex
import com.cubo.app.data.preferences.PreferenceKeys
import com.cubo.app.data.preferences.PreferenceSerializer
import com.cubo.app.di.modules.initDI
import com.cubo.app.presentation.extensions.initLogger


class CuboApplication : Application() {

    companion object {
        lateinit var dataStore: DataStore<AppPreference>
    }

    private val Context.partnerDataStore: DataStore<AppPreference> by dataStore(
        fileName = PreferenceKeys.DATA_STORE_FILE_NAME,
        serializer = PreferenceSerializer
    )

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        dataStore = applicationContext.partnerDataStore
        initDI()
        initLogger()
    }
}