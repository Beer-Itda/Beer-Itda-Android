package com.ddd4.synesthesia.beer.presentation.base

import android.app.Application
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.ThemeHelper
import com.ddd4.synesthesia.beer.ThemeHelper.KEY_SELECTED_THEME_MODE
import com.hjiee.core.manager.DataChangeManager
import com.hjiee.core.manager.UserInfoManager
import com.hjiee.core.provider.SharedPreferenceProvider
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

        // 데이터 변경 감지 observer manager
        DataChangeManager.init(this@BaseApplication)

        // 테마 설정
        ThemeHelper.getThemeMode(selectedThemeString()).let {
            ThemeHelper.applyTheme(it)
        }
    }

    private fun selectedThemeString(): String {
        return SharedPreferenceProvider(this).getPreferenceString(KEY_SELECTED_THEME_MODE).orEmpty()
    }

}