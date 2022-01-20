package com.android.topscare.domain.usecase

import android.content.Context
import android.provider.Settings
import com.android.topscare.domain.data.AppSettingPref
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AppSettingUseCase @Inject constructor(
    private val preferences: AppSettingPref,
    @ApplicationContext private val context: Context
) {
    fun saveBaseUrl(url: String) {
        preferences.savePref(BASE_URL, url)
    }
    fun getBaseUrl(): String? = preferences.getPref(BASE_URL, "http://192.168.1.36/")

    fun getHhName() = Settings.Global.getString(context.contentResolver, Settings.Global.DEVICE_NAME) ?: Settings.Secure.getString(context.contentResolver, "bluetooth_name")
    companion object {
        const val BASE_URL = "BASE_URL"
    }
}