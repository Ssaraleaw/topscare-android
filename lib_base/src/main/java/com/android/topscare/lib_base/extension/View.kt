package com.android.topscare.lib_base.extension

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.transition.TransitionManager
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

@BindingAdapter("app:animatedVisibility")
fun setAnimatedVisibility(target: View, isVisible: Boolean) {
    TransitionManager.beginDelayedTransition(target.rootView as ViewGroup)
    target.visibility = if (isVisible) View.VISIBLE else View.GONE
}
@BindingAdapter("app:isVisible")
fun isVisible(target: View, isVisible: Boolean) {
    target.isVisible = isVisible
}