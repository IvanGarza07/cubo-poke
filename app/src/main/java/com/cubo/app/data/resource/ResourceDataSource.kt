package com.cubo.app.data.resource

import android.app.KeyguardManager
import android.graphics.drawable.Drawable
import androidx.annotation.ArrayRes
import androidx.annotation.DimenRes
import androidx.annotation.StringRes

interface ResourceDataSource {

    fun getLanguage(): String

    fun getString(@StringRes resourceId: Int, params: Any? = null): String

    fun getString(@StringRes resourceId: Int, params: Any? = null, params2: Any? = null): String

    fun getStringArray(@ArrayRes resourceId: Int): Array<String>

    fun getDrawable(resource: Int): Drawable?

    fun getDimensionPixelSize(@DimenRes res: Int): Int

    fun getDimensionPixelOffset(@DimenRes res: Int): Int

    fun getDimension(@DimenRes res: Int): Float

    fun getKeyguardManager(): KeyguardManager?

    fun validateEmail(email: String): Boolean

    fun generateToast(string: String)

}