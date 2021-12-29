package com.hjiee.core.manager

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.hjiee.core.R
import com.hjiee.core.provider.SharedPreferenceProvider
import com.hjiee.core.util.log.L
import javax.inject.Inject

class FirebaseConfigManager @Inject constructor(
    private val preference: SharedPreferenceProvider
) {

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

    fun fetchRemoteConfig() {
        remoteConfig.fetchAndActivate().run {
            addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    setConfig()
                } else {
                    L.e(Throwable("complete but task is fail"))
                }
            }
            addOnFailureListener {
                L.e(Throwable("remote config failure"))
            }
        }
    }

    private fun setConfig() {
        setForceUpdateVersion(remoteConfig.getValue(UPDATE_VERSION_INFO).asString())
        setNoticeVersion(remoteConfig.getString(USE_NOTICE_VERSION))
        setNotice(remoteConfig.getString(NOTICE))
        setPrivacyPolicy(remoteConfig.getString(PRIVACY_POLICY))
        setTermsOfUse(remoteConfig.getString(TERMS_OF_USE))
        setReleaseNote(remoteConfig.getString(RELEASE_NOTE))
    }

    private fun setForceUpdateVersion(data: String) {
        preference.setValue(UPDATE_VERSION_INFO, data)
        L.d("강제 업데이트 버전정보 : $data")
    }


    private fun setNoticeVersion(data: String) {
        preference.setValue(USE_NOTICE_VERSION, data)
        L.d("링크 변경 버전정보 : $data")
    }

    private fun setNotice(data: String) {
        preference.setValue(NOTICE, data)
        L.d("공지사항 URL : $data")
    }

    private fun setPrivacyPolicy(data: String) {
        preference.setValue(PRIVACY_POLICY, data)
        L.d("개인정보 처리방침 URL : $data")
    }

    private fun setTermsOfUse(data: String) {
        preference.setValue(TERMS_OF_USE, data)
        L.d("이용약관 URL : $data")
    }

    private fun setReleaseNote(data: String) {
        preference.setValue(RELEASE_NOTE, data)
        L.d("릴리즈 노트 URL : $data")
    }

    companion object {
        const val USE_NOTICE_VERSION = "use_notice_version"
        const val NOTICE = "notice"
        const val PRIVACY_POLICY = "privacy_policy"
        const val TERMS_OF_USE = "terms_of_use"
        const val RELEASE_NOTE = "release_note"
        const val UPDATE_VERSION_INFO = "update_version_info"
        const val RECOMMEND_UPDATE_VERSION = "recommend_update_version"
        const val FORCE_UPDATE_VERSION = "force_update_version"
    }
}