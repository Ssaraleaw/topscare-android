package com.android.topscare.lib_base.utils

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.annotation.RequiresApi
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

open class SharedPreferenceUtil @Inject constructor(
    @ApplicationContext val context: Context,
    val gson: Gson,
) {
    open val prefName: String? = null

    private val sharedPreferences = getNonEncryptedSharedPreference()

    private fun getNonEncryptedSharedPreference() =
        context.getSharedPreferences(prefName ?: context.packageName, Context.MODE_PRIVATE)

    private val sharedPreferencesEditor: SharedPreferences.Editor = sharedPreferences.edit()

    @RequiresApi(Build.VERSION_CODES.M)
    private fun getEncryptedSharedPreference(): SharedPreferences {
        val keyGenParameterSpec = KeyGenParameterSpec.Builder(
            MasterKey.DEFAULT_MASTER_KEY_ALIAS,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        ).setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .setKeySize(KEY_SIZE)
            .build()
        val masterKeyAlias = MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
            .setKeyGenParameterSpec(keyGenParameterSpec)
            .build()
        return EncryptedSharedPreferences.create(
            context,
            prefName ?: context.packageName,
            masterKeyAlias,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    private fun delete(key: String) {
        if (sharedPreferences.contains(key)) {
            sharedPreferencesEditor.remove(key).commit()
        }
    }

    fun savePref(key: String, value: Any?) {
        delete(key)

        when {
            value is Boolean -> sharedPreferencesEditor.putBoolean(key, value)
            value is Int -> sharedPreferencesEditor.putInt(key, value)
            value is Float -> sharedPreferencesEditor.putFloat(key, value)
            value is Long -> sharedPreferencesEditor.putLong(key, value)
            value is String -> sharedPreferencesEditor.putString(key, value)
            value is Enum<*> -> sharedPreferencesEditor.putString(key, value.toString())
            value != null -> throw RuntimeException("Attempting to save non-primitive preference")
        }

        sharedPreferencesEditor.commit()
    }

    fun <T> saveObject(key: String, value: T) {
        val json = gson.toJson(value)
        savePref(key, json)
    }

    inline fun <reified T> getObject(key: String): T? {
        val json = getPref<String>(key)
        return gson.fromJson<Any>(json, T::class.java) as T?
    }

    fun <T> getPref(key: String): T? {
        return sharedPreferences.all[key] as T?
    }

    fun <T> getPref(key: String, defValue: T): T {
        val returnValue = sharedPreferences.all[key] as T?
        return returnValue ?: defValue
    }

    fun <T> saveList(key: String, value: List<T>) {
        val stringValue = gson.toJson(value)
        savePref(key, stringValue)
    }

    inline fun <reified T> getList(key: String): List<T> {
        val returnedValue = getPref<String>(key)
        return returnedValue.let {
            gson.fromJson<List<T>>(it as String, object : TypeToken<List<T>>() {}.type)
        } ?: emptyList()
    }

    fun clearAll() {
        sharedPreferencesEditor.clear()
    }

    companion object {
        const val KEY_SIZE = 256
    }

}