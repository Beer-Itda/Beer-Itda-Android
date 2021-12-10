package com.hjiee.core.util.log.timber

import android.util.Log
import com.hjiee.core.util.log.CrashlyticsLog
import timber.log.Timber

class CrashlyticsTree : Timber.Tree() {

    override fun log(priority: Int, tag: String?, message: String, throwable: Throwable?) {
        super.log(priority, tag, message, throwable)
        if (priority == Log.ERROR) {
            CrashlyticsLog.recordException(
                if (throwable != null) {
                    StackTraceRecorder("[$tag] $message", throwable)
                } else {
                    StackTraceRecorder("[$tag] $message")
                }
            )
        } else {
            CrashlyticsLog.log(message)
        }
    }
}