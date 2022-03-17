package com.cubo.data.source

import com.cubo.data.params.LoginUserParams

interface UserDataSource {

    suspend fun loginUser(loginParams: LoginUserParams): String
}