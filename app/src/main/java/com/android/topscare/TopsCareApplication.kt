package com.android.topscare

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import com.android.topscare.lib_base.utils.setLocale
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TopsCareApplication : Application() {
    override fun attachBaseContext(base: Context?) {
        val localeContext = base?.setLocale()
        super.attachBaseContext(localeContext)
    }
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        setLocale()
    }
}