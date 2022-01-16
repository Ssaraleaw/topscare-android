package com.android.topscare.lib_base.extension

import android.net.Uri
import androidx.navigation.NavController
import java.lang.Exception

fun NavController.deeplink(uri: Uri) {
    try {
        navigate(uri)
    } catch (ex: Exception) {

    }
}