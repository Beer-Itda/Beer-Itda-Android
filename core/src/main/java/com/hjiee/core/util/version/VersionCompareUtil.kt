package com.hjiee.core.util.version

import com.hjiee.core.ext.orZero
import com.hjiee.core.manager.AppUpdate
import com.hjiee.core.util.log.L
import java.util.*

object VersionCompareUtil {

    /**
     * Here is representation of Version code generation from version name.
     *
     *  *--------- major version
     *  |  *------ minor version
     *  |  |  *--- patch version
     *  |  |  |*-- buildType (dev/alpha/beta/rc/release)
     *  |  |  ||*- flavor - disabled.
     *  |  |  |||
     *  X00X00XXX
     * so
     *  1.13.20-alpha-v19 = 10130201
     * see details below:
     *  *--------- 1 is major version
     *  |  *------ 13 is minor version
     *  |  |  *--- 20 is patch version
     *  |  |  |*-- type 1 is alpha.
     *  |  |  ||*- flavor 0 (disabled)
     *  |  |  |||
     *  101302010
     */
    private const val flavorDigit = 1
    private const val typeDigit = flavorDigit * 10
    private const val patchDigit = typeDigit * 10
    private const val minorDigit = patchDigit * 1000
    private const val majorDigit = minorDigit * 1000

    fun resultVersionCompare(
        currentVersion: String,
        forceVersion: String,
        recommendVersion: String
    ): AppUpdate {
        val currentVersionWeight = getWeight(currentVersion)
        val forceVersionWeight = getWeight(forceVersion)
        val recommendVersionWeight = getWeight(recommendVersion)

        return when {
            currentVersionWeight < forceVersionWeight -> {
                L.d("UPDATE","need to force update from $currentVersion")
                AppUpdate.Force
            }
            currentVersionWeight < recommendVersionWeight -> {
                L.d("UPDATE","need to recommend update from $currentVersion")
                AppUpdate.Recommend
            }
            else -> {
                AppUpdate.None
            }
        }
    }

    /**
     * 가중치로 환산하여 반환
     * store에 배포되는 버전이 가장 높음
     * ex) 2.0.0 > 2.0.0-dev
     */
    fun getWeight(version: String): Int {
        val digit = version.split(".", "-").map { it.toIntOrNull().orZero() }

        return (digit.getOrNull(0).orZero() * majorDigit) +
                (digit.getOrNull(1).orZero() * minorDigit) +
                (digit.getOrNull(2).orZero() * patchDigit) +
                (digit.getOrNull(3).orZero() + getTypeWeight(version))
    }

    /**
     * 타입별 가중치
     */
    private fun getTypeWeight(version: String): Int {
        return try {
            when (version.split("-").getOrNull(1)?.toUpperCase(Locale.getDefault())) {
                VersionType.DEV.name -> {
                    VersionType.DEV.weight
                }
                VersionType.ALPHA.name -> {
                    VersionType.ALPHA.weight
                }
                VersionType.BETA.name -> {
                    VersionType.BETA.weight
                }
                VersionType.RC.name -> {
                    VersionType.RC.weight
                }
                VersionType.RELEASE.name -> {
                    VersionType.RELEASE.weight
                }
                else -> {
                    VersionType.STORE.weight
                }
            }
        } catch (e: Exception) {
            L.e(e, version)
            0
        }
    }

    private enum class VersionType(val weight: Int) {
        DEV(1),
        ALPHA(2),
        BETA(3),
        RC(4),
        RELEASE(5),
        STORE(6)
    }
}