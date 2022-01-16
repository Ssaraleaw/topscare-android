package com.android.topscare.lib_base.utils

import android.util.Log
import com.android.topscare.lib_base.BuildConfig

class NLog {
    companion object {
        private var showLog: Boolean = BuildConfig.DEBUG
        private const val MAX_LOG_LENGTH = 4076

        fun setShowLog(showLog: Boolean) {
            NLog.showLog = showLog
        }

        fun d(tag: String?, msg: String?) {
            if (showLog) {
                Log.d(tag, msg.orEmpty())
            }
        }

        fun v(tag: String?, msg: String?) {
            if (showLog) {
                Log.v(tag, msg.orEmpty())
            }
        }

        fun i(tag: String?, msg: String?) {
            if (showLog) {
                Log.i(tag, msg.orEmpty())
            }
        }

        fun e(tag: String?, msg: String?) {
            if (showLog) {
                Log.e(tag, msg.orEmpty())
            }
        }

        fun e(tag: String?, msg: String?, tr: Throwable?) {
            if (showLog) {
                Log.e(tag, msg, tr)
            }
        }

        fun w(tag: String?, msg: String?) {
            if (showLog) {
                Log.w(tag, msg.orEmpty())
            }
        }

        fun largeLog(tag: String?, content: String) {
            var offset = 0
            while (offset + MAX_LOG_LENGTH <= content.length) {
                Log.d(
                    tag,
                    content.substring(offset, MAX_LOG_LENGTH.let { offset += it; offset }).replace("&quot;", "\"")
                )
            }
            if (offset < content.length) {
                Log.d(tag, content.substring(offset).replace("&quot;", "\""))
            }
        }
    }
}