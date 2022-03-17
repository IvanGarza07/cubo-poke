package com.cubo.app.data.server.utils

import com.cubo.data.params.LoginUserParams

fun LoginUserParams.toServerParams(): HashMap<String, Any> {
    val params = HashMap<String, Any>()
    params[ServerParams.EMAIL] = this.email
    params[ServerParams.PASSWORD] = this.password
    return params
}