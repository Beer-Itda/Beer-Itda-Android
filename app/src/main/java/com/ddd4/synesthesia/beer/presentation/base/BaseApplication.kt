package com.ddd4.synesthesia.beer.presentation.base

import android.app.Application
import com.ddd4.synesthesia.beer.R
import com.hjiee.core.manager.UserInfoManager
import com.hjiee.core.util.log.CrashlyticsLog
import com.hjiee.core.util.log.L
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // Firebase 로그 초기화
        CrashlyticsLog.init()

        // Timber 로그 초기화
        L.init()

        // SDK 초기화 V2
        KakaoSdk.init(this@BaseApplication, getString(R.string.kakao))

        // user manager 초기화
        UserInfoManager.init(this@BaseApplication)
    }
}