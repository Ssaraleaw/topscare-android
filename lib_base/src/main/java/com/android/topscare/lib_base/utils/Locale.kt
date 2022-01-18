package com.android.topscare.lib_base.utils

import android.content.Context
import android.content.res.Configuration
import android.preference.PreferenceManager
import androidx.annotation.Keep
import java.util.*

const val SELECTED_LANGUAGE = "Locale.Selected.Language"

fun Context.setLocale(): Context = setNewLocale(getLanguage())

fun Context.setNewLocale(language: String): Context {
    persistLanguage(this, language)
    return updateResource(this, language)
}

private fun updateResource(context: Context, language: String): Context {
    val locale = Locale(language)
    Locale.setDefault(locale)

    val resource = context.resources
    val config = Configuration(resource.configuration)
    config.setLocale(locale)
    return context.createConfigurationContext(config)
}

fun Context.getLanguage(defaultLanguage: String = Locale.getDefault().language): String {
    val preferences = PreferenceManager.getDefaultSharedPreferences(this)
    return preferences.getString(SELECTED_LANGUAGE, defaultLanguage) ?: defaultLanguage
}

private fun persistLanguage(context: Context, language: String?) {
    val preferences = PreferenceManager.getDefaultSharedPreferences(context)
    val editor = preferences.edit()

    editor.putString(SELECTED_LANGUAGE, language)
    editor.apply()
}

@Keep
enum class Language(val value: String) {
    TH("th"),
    EN("en");

    companion object {
        fun from(value: String): Language =
            when (value) {
                "th" -> TH
                "en" -> EN
                else -> TH
            }
    }

    fun languageCodeForKommunicate(): String {
        when (this) {
            TH -> return "th"
            EN -> return "en-US"
        }
    }
}