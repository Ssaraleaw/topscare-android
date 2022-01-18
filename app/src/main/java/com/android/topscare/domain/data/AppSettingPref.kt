package com.android.topscare.domain.data

import android.content.Context
import com.android.topscare.lib_base.utils.SharedPreferenceUtil
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AppSettingPref @Inject constructor(
    @ApplicationContext context: Context,
    gson: Gson
) : SharedPreferenceUtil(context, gson) {
    override val prefName: String
        get() = "app_setting"
}