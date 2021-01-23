package com.ddd4.synesthesia.beer.presentation.base

import android.app.Application
import com.ddd4.synesthesia.beer.BuildConfig
import com.ddd4.synesthesia.beer.R
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber


@HiltAndroidApp
class BaseApplication : Application() {

    val clientId by lazy { getString(R.string.kakao) }

    override fun onCreate() {
        super.onCreate()
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(!BuildConfig.DEBUG)
        // SDK 초기화 V2
        KakaoSdk.init(this@BaseApplication,getString(R.string.kakao))

        // Timber 로그 초기화
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}