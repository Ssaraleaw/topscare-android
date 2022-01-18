package com.android.topscare.domain.usecase

import com.android.topscare.domain.data.AppSettingPref
import javax.inject.Inject

class AppSettingUseCase @Inject constructor(
    private val preferences: AppSettingPref
) {
    fun saveBaseUrl(url: String) {
        preferences.savePref(BASE_URL, url)
    }
    fun getBaseUrl(): String? = preferences.getPref(BASE_URL, "http://192.168.1.36/")
    companion object {
        const val BASE_URL = "BASE_URL"
    }
}