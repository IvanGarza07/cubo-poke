package com.cubo.app.presentation.utils


object AppKeys {

    init {
        System.loadLibrary("native-lib")
    }

    /*##########################################################
                            DOMAIN
    ##########################################################*/
    external fun domain(): String
}
