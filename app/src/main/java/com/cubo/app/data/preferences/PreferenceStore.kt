package com.cubo.app.data.preferences

import androidx.datastore.core.DataStore
import com.cubo.app.AppPreference
import com.cubo.data.source.PreferenceDataSource
import kotlinx.coroutines.flow.flow

class PreferenceStore(private val dataStore: DataStore<AppPreference>) :
    PreferenceDataSource {

    override fun getToken() = flow {
        dataStore.data.collect { emit(it.token) }
    }

    override suspend fun saveToken(token: String) {
        dataStore.updateData { currentPreferences ->
            currentPreferences.copy(token = token)
        }
    }
}