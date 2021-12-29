package com.hjiee.core.manager

import android.content.Context
import com.hjiee.core.manager.FirebaseConfigManager.Companion.FORCE_UPDATE_VERSION
import com.hjiee.core.manager.FirebaseConfigManager.Companion.RECOMMEND_UPDATE_VERSION
import com.hjiee.core.manager.FirebaseConfigManager.Companion.UPDATE_VERSION_INFO
import com.hjiee.core.provider.SharedPreferenceProvider
import com.hjiee.core.util.log.L
import com.hjiee.core.util.version.VersionCompareUtil.resultVersionCompare
import org.json.JSONObject
import javax.inject.Inject

class VersionManager @Inject constructor(
    private val context: Context,
    private val preference: SharedPreferenceProvider
) {

    val version: String
        get() = try {
            context.packageManager.getPackageInfo(context.packageName, 0).versionName
        } catch (e: Exception) {
            L.e(e)
            ""
        }

    var lastVisitTime
        get() = preference.getPreferenceString(KEY_LAST_VISIT)
        set(value) = preference.setValue(KEY_LAST_VISIT, value)

    fun updateInfo(): AppUpdate {
        val versionJsonObject = preference.getPreferenceString(UPDATE_VERSION_INFO)
        var forceUpdateVersion = ""
        var recommendUpdateVersion = ""

        runCatching {
            JSONObject(versionJsonObject).run {
                forceUpdateVersion = get(FORCE_UPDATE_VERSION).toString()
                recommendUpdateVersion = get(RECOMMEND_UPDATE_VERSION).toString()
            }
        }.onFailure {
            L.e(it, "currentVersion : ${version} / targetVersionInfo : $versionJsonObject")
        }


        return resultVersionCompare(
            currentVersion = version,
            forceVersion = forceUpdateVersion,
            recommendVersion = recommendUpdateVersion
        )
    }

    companion object {
        const val KEY_LAST_VISIT = "last_visit"
    }
}

sealed class AppUpdate {
    object Force : AppUpdate()
    object Recommend : AppUpdate()
    object None : AppUpdate()
}