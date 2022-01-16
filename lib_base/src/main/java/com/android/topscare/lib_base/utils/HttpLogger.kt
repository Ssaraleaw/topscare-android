package com.android.topscare.lib_base.utils

import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import okhttp3.logging.HttpLoggingInterceptor

class HttpLogger : HttpLoggingInterceptor.Logger {
    override fun log(message: String) {
        val logName = "OkHttp"
        if (!message.startsWith("{") && !message.startsWith("[")) {
            NLog.largeLog(logName, message)
            return
        }
        try {
            val prettyPrintJson =
                GsonBuilder().setPrettyPrinting().create().toJson(JsonParser.parseString(message))
            NLog.largeLog(logName, prettyPrintJson)
        } catch (m: JsonSyntaxException) {
            NLog.d(logName, "html header parse failed")
            m.printStackTrace()
            NLog.largeLog(logName, message)
        }
    }
}