package com.cubo.app.data.server.user

import com.cubo.app.data.server.results.LoginResult
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.HeaderMap
import retrofit2.http.POST

interface UserService {

    @POST("login")
    suspend fun loginUser(
        @HeaderMap headers: Map<String, String>,
        @Body body: HashMap<String, Any>
    ): Response<LoginResult>

}