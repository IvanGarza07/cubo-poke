package com.cubo.data.repository

import com.cubo.data.params.LoginUserParams
import com.cubo.data.source.PreferenceDataSource
import com.cubo.data.source.UserDataSource

class UserRepository(
    private val userDataSource: UserDataSource,
    private val preferenceDataSource: PreferenceDataSource
) {

    suspend fun loginUser(loginUserParams: LoginUserParams): String {
        val token = userDataSource.loginUser(loginUserParams)
        preferenceDataSource.saveToken(token)
        return token
    }

}