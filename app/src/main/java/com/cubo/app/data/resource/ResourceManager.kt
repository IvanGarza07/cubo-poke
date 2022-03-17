package com.cubo.app.data.resource

import android.app.KeyguardManager
import android.content.Context
import android.content.res.Resources
import android.util.Patterns
import android.widget.Toast
import androidx.annotation.DimenRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import java.util.*

class ResourceManager(val context: Context): ResourceDataSource {

    private val resources: Resources = context.resources

    override fun getLanguage(): String = if (Locale.getDefault().language.equals("en", ignoreCase = true)) {
        "en"
    } else {
        "es"
    }

    override fun getString(@StringRes resourceId: Int, params: Any?) = resources.getString(
        resourceId,
        params
    )

    override fun getString(@StringRes resourceId: Int, params: Any?, params2: Any?) =
        resources.getString(
            resourceId,
            params,
            params2
        )

    override fun getStringArray(resourceId: Int): Array<String> {
        return resources.getStringArray(resourceId)
    }

    override fun getDrawable(resource: Int) = ContextCompat.getDrawable(context, resource)

    override fun getDimensionPixelSize(@DimenRes res: Int) = resources.getDimensionPixelSize(res)

    override fun getDimensionPixelOffset(@DimenRes res: Int) = resources.getDimensionPixelOffset(res)

    override fun getDimension(@DimenRes res: Int) = resources.getDimension(res)

    override fun getKeyguardManager() =
        context.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager?

    override fun validateEmail(email: String) = Patterns.EMAIL_ADDRESS.matcher(email).matches()

    override fun generateToast(string: String) {
        Toast.makeText(context, string, Toast.LENGTH_LONG).show()
    }
}
