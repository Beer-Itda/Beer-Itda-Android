package com.ddd4.synesthesia.beer.util.log

import com.google.firebase.crashlytics.FirebaseCrashlytics

object CrashlyticsLog {

    fun recordException(throwable: Throwable) {
        FirebaseCrashlytics.getInstance().recordException(throwable)
    }

    fun log(message: String) {
        FirebaseCrashlytics.getInstance().log(message)
    }
}