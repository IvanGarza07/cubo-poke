/*
 *  Create by Ivan Garza on 1/11/22, 9:29 PM.
 *
 *  Copyright (c) year Ivan Garza.
 *  All rights reserved.
 */

package com.cubo.data.source

import kotlinx.coroutines.flow.Flow

interface PreferenceDataSource {

    fun getToken(): Flow<String>

    suspend fun saveToken(token: String)

}