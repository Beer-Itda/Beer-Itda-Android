package com.ddd4.synesthesia.beer.presentation.ui.splash.view

import com.ddd4.synesthesia.beer.R
import com.hjiee.core.manager.AppUpdate
import com.hjiee.core.manager.VersionManager
import com.hjiee.core.provider.StringProvider
import javax.inject.Inject

class SplashStringProvider @Inject constructor(
    private val stringProvider: StringProvider,
    private val versionManager: VersionManager
) {

    private val currentVersion get() = "${stringProvider.getStringRes(R.string.current_version)} : ${versionManager.version}"

    fun getString(type: AppUpdate): String {
        return when (type) {
            is AppUpdate.Force -> {
                "${stringProvider.getStringRes(R.string.force_update)}\n$currentVersion"
            }
            is AppUpdate.Recommend -> {
                "${stringProvider.getStringRes(R.string.recommend_update)}\n$currentVersion"
            }
            is AppUpdate.None -> {
                ""
            }
        }
    }
}