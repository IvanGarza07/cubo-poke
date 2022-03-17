package com.cubo.app.data.server.user

import com.cubo.app.data.server.utils.ServerUtils
import com.cubo.app.data.server.utils.toServerParams
import com.cubo.data.params.LoginUserParams
import com.cubo.data.source.UserDataSource

class UserServer(private val userService: UserService) : UserDataSource {

    override suspend fun loginUser(loginParams: LoginUserParams): String {
        val response = userService.loginUser(
            ServerUtils.getApiServiceHeaders(false),
            loginParams.toServerParams()
        )
        var token = ""
        if (response.isSuccessful) {
            response.body()?.let {
                token = it.token
            }
        }
        return token
    }
}