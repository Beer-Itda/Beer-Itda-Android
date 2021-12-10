package com.hjiee.core.manager

import android.content.Context
import android.content.pm.PackageManager
import com.hjiee.core.provider.SharedPreferenceProvider
import javax.inject.Inject

class VersionManager @Inject constructor(
    private val context: Context,
    private val sharedPreference: SharedPreferenceProvider
) {

    companion object {
        const val KEY_LAST_VISIT = "last_visit"
    }

    val version
        get() = try {
            context.packageManager.getPackageInfo(context.packageName, 0).versionName
        } catch (e: Exception) {
            ""
        }

    var lastVisitTime
        get() = sharedPreference.getPreferenceString(KEY_LAST_VISIT)
        set(value) = sharedPreference.setValue(KEY_LAST_VISIT, value)
}