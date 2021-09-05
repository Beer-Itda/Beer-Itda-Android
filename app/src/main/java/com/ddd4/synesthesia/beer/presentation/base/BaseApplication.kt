package com.ddd4.synesthesia.beer.presentation.base

import android.app.Application
import com.ddd4.synesthesia.beer.BuildConfig
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.util.log.L
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber


@HiltAndroidApp
class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // SDK 초기화 V2
        KakaoSdk.init(this@BaseApplication, getString(R.string.kakao))

        // Firebase 로그 초기화
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(!BuildConfig.DEBUG)

        // Timber 로그 초기화
        L.init()
    }
}