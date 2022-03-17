package com.cubo.app.data.preferences

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.cubo.app.AppPreference
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

object PreferenceSerializer : Serializer<AppPreference> {

    override val defaultValue: AppPreference
        get() = AppPreference(
            token = ""
        )

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun readFrom(input: InputStream): AppPreference {
        try {
            return AppPreference.ADAPTER.decode(input)
        } catch (exception: IOException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun writeTo(t: AppPreference, output: OutputStream) {
        AppPreference.ADAPTER.encode(output, t)
    }
}