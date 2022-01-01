package com.ddd4.synesthesia.beer.presentation.ui.splash.view

import com.ddd4.synesthesia.beer.R
import com.hjiee.core.manager.UpdateRequiredStatus
import com.hjiee.core.manager.VersionManager
import com.hjiee.core.provider.StringProvider
import javax.inject.Inject

class SplashStringProvider @Inject constructor(
    private val stringProvider: StringProvider,
    private val versionManager: VersionManager
) {

    private val currentVersion get() = "${stringProvider.getStringRes(R.string.current_version)} : ${versionManager.version}"
    private val forceVersion get() = "${stringProvider.getStringRes(R.string.force_update_version)} : ${versionManager.forceUpdateVersion}"
    private val recommendVersion get() = "${stringProvider.getStringRes(R.string.recommend_update)} : ${versionManager.recommendUpdateVersion}"

    fun getString(type: UpdateRequiredStatus): String {
        return when (type) {
            is UpdateRequiredStatus.Force -> {
                "${stringProvider.getStringRes(R.string.force_update)}\n\n$forceVersion\n$currentVersion"
            }
            is UpdateRequiredStatus.Recommend -> {
                "${stringProvider.getStringRes(R.string.recommend_update)}\n\n$recommendVersion\n$currentVersion"
            }
            is UpdateRequiredStatus.None -> {
                ""
            }
        }
    }
}