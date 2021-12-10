package com.ddd4.synesthesia.beer.presentation.ui.splash.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.presentation.base.BaseViewModel
import com.ddd4.synesthesia.beer.presentation.ui.login.model.LoginActionEntity
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.hjiee.core.provider.INoticeStringResourceProvider.Code
import com.hjiee.core.provider.SharedPreferenceProvider
import com.hjiee.core.provider.StringProvider
import com.hjiee.core.util.log.CrashlyticsLog
import com.hjiee.core.util.log.L
import com.hjiee.domain.usecase.login.GetTokenUseCase
import com.hjiee.domain.usecase.login.LoginUseCase
import kotlinx.coroutines.launch

class SplashViewModel @ViewModelInject constructor(
    private val useCase: LoginUseCase,
    private val tokenUseCase: GetTokenUseCase,
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

    suspend fun autoLogin() {
        tokenUseCase.execute { accessToken ->
            if (accessToken.isEmpty()) {
                notifyActionEvent(LoginActionEntity.FailLogin)
            } else {
                viewModelScope.launch {
                    runCatching {
                        useCase.execute(accessToken)
                    }.onSuccess {
                        notifyActionEvent(LoginActionEntity.SuccessLogin)
                    }.onFailure {
                        notifyActionEvent(LoginActionEntity.FailLogin)
                    }
                }
            }
        }
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

                    preference.setValue(stringProvider.getString(Code.NOTICE), notice)
                    preference.setValue(
                        stringProvider.getString(Code.PRIVACY_POLICY),
                        privacyPolicy
                    )
                    preference.setValue(
                        stringProvider.getString(Code.TERMS_OF_USE),
                        termsOfUse
                    )
                    preference.setValue(
                        stringProvider.getString(Code.RELEASE_NOTE),
                        releaseNote
                    )

                    CrashlyticsLog.setCustomKey(
                        key = stringProvider.getString(Code.USE_NOTICE_VERSION),
                        value = useNoiceVersion
                    )
                    CrashlyticsLog.setCustomKey(
                        key = stringProvider.getString(Code.NOTICE),
                        value = notice
                    )
                    CrashlyticsLog.setCustomKey(
                        key = stringProvider.getString(Code.PRIVACY_POLICY),
                        value = privacyPolicy
                    )
                    CrashlyticsLog.setCustomKey(
                        key = stringProvider.getString(Code.TERMS_OF_USE),
                        value = termsOfUse
                    )
                    CrashlyticsLog.setCustomKey(
                        key = stringProvider.getString(Code.RELEASE_NOTE),
                        value = releaseNote
                    )
                    L.d("completed $useNoiceVersion")
                } else {
                    L.e(Throwable("complete but task is fail"))
                }
            }
            addOnFailureListener {
                L.e(Throwable("remote config failure"))
            }
        }
    }
}