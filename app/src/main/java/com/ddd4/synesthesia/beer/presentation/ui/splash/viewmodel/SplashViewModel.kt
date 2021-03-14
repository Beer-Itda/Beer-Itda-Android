package com.ddd4.synesthesia.beer.presentation.ui.splash.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.domain.repository.LoginRepository
import com.ddd4.synesthesia.beer.presentation.base.BaseViewModel
import com.ddd4.synesthesia.beer.util.provider.INoticeStringResourceProvider.Code
import com.ddd4.synesthesia.beer.util.provider.SharedPreferenceProvider
import com.ddd4.synesthesia.beer.util.provider.StringProvider
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.kakao.sdk.auth.model.OAuthToken
import timber.log.Timber

class SplashViewModel @ViewModelInject constructor(
    private val loginRepository: LoginRepository,
    private val preference: SharedPreferenceProvider,
    private val stringProvider: StringProvider
) : BaseViewModel() {

    private val configSettings by lazy {
        FirebaseRemoteConfigSettings.Builder().apply {
            fetchTimeoutInSeconds = 3600
        }.build()
    }
    private val remoteConfig by lazy {
        FirebaseRemoteConfig.getInstance().apply {
            setConfigSettingsAsync(configSettings)
            setDefaultsAsync(R.xml.remote_config_defaults)
        }
    }

    fun tokenInfo(tokenInfo: ((OAuthToken?) -> Unit)? = null) = loginRepository.tokenInfo {
        Timber.tag("tokenInfo").d("kakao token info : ${it?.accessToken}")
        tokenInfo?.invoke(it)
    }

    fun getRemoteConfig() {
        remoteConfig.fetchAndActivate().run {
            addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val useNoiceVersion =
                        remoteConfig.getString(stringProvider.getString(Code.USE_NOTICE_VERSION))
                    val notice = remoteConfig.getString(stringProvider.getString(Code.NOTICE))
                    val privacyPolicy =
                        remoteConfig.getString(stringProvider.getString(Code.PRIVACY_POLICY))
                    val termsOfUse =
                        remoteConfig.getString(stringProvider.getString(Code.TERMS_OF_USE))
                    val releaseNote =
                        remoteConfig.getString(stringProvider.getString(Code.RELEASE_NOTE))

                    preference.setPreference(stringProvider.getString(Code.NOTICE), notice)
                    preference.setPreference(
                        stringProvider.getString(Code.PRIVACY_POLICY),
                        privacyPolicy
                    )
                    preference.setPreference(
                        stringProvider.getString(Code.TERMS_OF_USE),
                        termsOfUse
                    )
                    preference.setPreference(
                        stringProvider.getString(Code.RELEASE_NOTE),
                        releaseNote
                    )

                    FirebaseCrashlytics.getInstance().setCustomKey(
                        stringProvider.getString(Code.USE_NOTICE_VERSION),
                        useNoiceVersion
                    )
                    FirebaseCrashlytics.getInstance()
                        .setCustomKey(stringProvider.getString(Code.NOTICE), notice)
                    FirebaseCrashlytics.getInstance()
                        .setCustomKey(stringProvider.getString(Code.PRIVACY_POLICY), privacyPolicy)
                    FirebaseCrashlytics.getInstance()
                        .setCustomKey(stringProvider.getString(Code.TERMS_OF_USE), termsOfUse)
                    FirebaseCrashlytics.getInstance()
                        .setCustomKey(stringProvider.getString(Code.RELEASE_NOTE), releaseNote)
                    FirebaseCrashlytics.getInstance().log("completed $useNoiceVersion")
                } else {
                    FirebaseCrashlytics.getInstance()
                        .recordException(Throwable("complete but task is fail"))
                }
            }
            addOnFailureListener {
                FirebaseCrashlytics.getInstance()
                    .recordException(Throwable("remote config failure"))
            }
        }
    }
}