/*
 *  Create by Ivan Garza on 1/20/22, 2:34 PM.
 *
 *  Copyright (c) year Ivan Garza.
 *  All rights reserved.
 */

package com.cubo.app.data.server.utils

object ServerUtils {

    fun getApiServiceHeaders(withAuth: Boolean, token: String = ""): Map<String, String> {
        return if (withAuth) {
            mapOf(
                ServerParams.AUTHORIZATION to "Bearer $token",
                ServerParams.ACCEPT_HEADER to ServerParams.JSON_CONTENT_TYPE,
                ServerParams.CONTENT_TYPE_HEADER to ServerParams.JSON_CONTENT_TYPE
            )
        } else {
            mapOf(
                ServerParams.ACCEPT_HEADER to ServerParams.JSON_CONTENT_TYPE,
                ServerParams.CONTENT_TYPE_HEADER to ServerParams.JSON_CONTENT_TYPE
            )
        }
    }

}