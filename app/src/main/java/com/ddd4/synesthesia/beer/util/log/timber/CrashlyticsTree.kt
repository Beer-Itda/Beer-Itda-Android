package com.ddd4.synesthesia.beer.util.log.timber

import android.util.Log
import com.ddd4.synesthesia.beer.util.log.CrashlyticsLog
import timber.log.Timber

class CrashlyticsTree : Timber.Tree() {

    override fun log(priority: Int, tag: String?, message: String, throwable: Throwable?) {
        super.log(priority, tag, message, throwable)
        if (priority == Log.ERROR) {
            CrashlyticsLog.recordException(
                if (throwable != null) {
                    Exception("[$tag] $message", throwable)
                } else {
                    Exception("[$tag] $message")
                }
            )
        } else {
            CrashlyticsLog.log(message)
        }
    }
}