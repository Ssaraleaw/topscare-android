package com.android.topscare.lib_base.extension

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.android.topscare.lib_base.R

fun <T> LifecycleOwner.observe(liveData: LiveData<T>, action: (t: T) -> Unit) {
    liveData.observe(this, Observer {
        action(it)
    })
}
fun Context.showChromeCustomTab(url: Uri) {
    val builder = CustomTabsIntent.Builder().apply {
        setDefaultColorSchemeParams(
            CustomTabColorSchemeParams.Builder()
                .setToolbarColor(resources.getColor(R.color.primary, theme)).build()
        )
    }
    val customTabIntent = builder.build()
    if (isChromeInstalled()) {
        customTabIntent.intent.setPackage("com.android.chrome")
    }

    try {
        customTabIntent.launchUrl(this, url)
    } catch (e: ActivityNotFoundException) {
        e.printStackTrace()
    }
}

private fun Context.isChromeInstalled(): Boolean {
    return try {
        packageManager.getPackageInfo("com.android.chrome", 0)
        true
    } catch (e: PackageManager.NameNotFoundException) {
        //chrome is not installed on the device
        false
    }
}