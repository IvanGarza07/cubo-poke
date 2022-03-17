package com.cubo.usecases

import com.cubo.data.params.LoginUserParams
import com.cubo.data.repository.UserRepository

class UserUseCases(private val userRepository: UserRepository) {

    suspend fun invokeLoginUser(loginUserParams: LoginUserParams) =
        userRepository.loginUser(loginUserParams)

}