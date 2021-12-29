package com.hjiee.core.version

import com.hjiee.core.manager.AppUpdate
import com.hjiee.core.util.version.VersionCompareUtil
import com.hjiee.core.util.version.VersionCompareUtil.resultVersionCompare
import org.junit.Test

class VersionCompareTest {

    private val current = "2.0.0"

    @Test
    fun `강제 업데이트`() {
        assert(
            isUpdate(
                currentVersion = current,
                forceVersion = "2.1.0",
                recommendVersion = "3.0.0",
                updateType = AppUpdate.Force
            )
        )
    }

    @Test
    fun `권장 업데이트`() {
        assert(
            isUpdate(
                currentVersion = current,
                forceVersion = "1.0.0",
                recommendVersion = "2.0.1",
                updateType = AppUpdate.Recommend
            )
        )
    }

    @Test
    fun `업데이트 대상이 아님`() {
        assert(
            isUpdate(
                currentVersion = current,
                forceVersion = "1.0.0",
                recommendVersion = "1.0.0",
                updateType = AppUpdate.None
            )
        )
    }


    private fun isUpdate(
        currentVersion: String,
        forceVersion: String,
        recommendVersion: String,
        updateType: AppUpdate
    ): Boolean {
        printOut(
            currentVersion = currentVersion,
            forceVersion = forceVersion,
            recommendVersion = recommendVersion
        )

        return resultVersionCompare(
            currentVersion = currentVersion,
            forceVersion = forceVersion,
            recommendVersion = recommendVersion
        ) == updateType
    }

    private fun printOut(
        currentVersion: String,
        forceVersion: String,
        recommendVersion: String
    ) {
        val currentVersionWeight = VersionCompareUtil.getWeight(currentVersion)
        val forceVersionWeight = VersionCompareUtil.getWeight(forceVersion)
        val recommendVersionWeight = VersionCompareUtil.getWeight(recommendVersion)

        when {
            currentVersionWeight < forceVersionWeight -> {
                println("${currentVersionWeight} :: $currentVersion")
                println("    ∧    Force")
                println("${forceVersionWeight} :: $forceVersion")
            }
            currentVersionWeight < recommendVersionWeight -> {
                println("${currentVersionWeight} :: $currentVersion")
                println("    ∧    Recommend")
                println("${recommendVersionWeight} :: $recommendVersion")
            }
            else -> {
                println("${currentVersionWeight} :: $currentVersion")
                println("    ∨    None")
                println("${forceVersionWeight} :: $forceVersion")
                println("${recommendVersionWeight} :: $recommendVersion")
            }
        }
        println()
        println("---------------------------------------------------------")
        println()
    }
}