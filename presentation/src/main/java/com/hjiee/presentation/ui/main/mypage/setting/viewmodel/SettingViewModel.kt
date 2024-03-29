package com.hjiee.presentation.ui.main.mypage.setting.viewmodel

import androidx.lifecycle.viewModelScope
import com.hjiee.ThemeHelper
import com.hjiee.ThemeHelper.KEY_SELECTED_THEME_MODE
import com.hjiee.core.manager.VersionManager
import com.hjiee.core.provider.SharedPreferenceProvider
import com.hjiee.data.source.local.InfomationsData
import com.hjiee.domain.usecase.login.LogoutUseCase
import com.hjiee.presentation.base.BaseViewModel
import com.hjiee.presentation.ui.main.mypage.setting.item.SettingItemViewModel
import com.hjiee.presentation.ui.main.mypage.setting.model.SettingActionEntity
import com.hjiee.presentation.ui.main.mypage.setting.view.SettingStringProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val versionManager: VersionManager,
    private val logoutUseCase: LogoutUseCase,
    private val stringProvider: SettingStringProvider,
    private val preference: SharedPreferenceProvider
) : BaseViewModel() {

    val appVersion by lazy { versionManager.version }

    fun init() {
        notifyActionEvent(SettingActionEntity.UpdateItem(generateInfoList()))
    }

    fun logOut() {
        viewModelScope.launch {
            runCatching {
                logoutUseCase.execute(
                    success = {
                        preference.clear()
                        notifyActionEvent(SettingActionEntity.LogOut)
                    },
                    failure = {
                        throwMessage(stringProvider.getError())
                    }
                )
            }
        }
    }

    fun getSelectedThemePosition(): Int {
        return ThemeHelper.getThemeMode(
            preference.getPreferenceString(KEY_SELECTED_THEME_MODE).orEmpty()
        ).ordinal
    }

    fun saveTheme(position: Int) {
        val selectedTheme = ThemeHelper.getThemeMode(position)
        preference.setValue(KEY_SELECTED_THEME_MODE, selectedTheme.name)
    }

    private fun generateInfoList(): List<SettingItemViewModel> = arrayListOf(
        // 공지사항
        SettingItemViewModel(
            InfomationsData.NOTICE.title,
            InfomationsData.NOTICE.type,
            eventNotifier = this@SettingViewModel
        ),
        // 문의하기
        SettingItemViewModel(
            InfomationsData.CONTACT.title,
            InfomationsData.CONTACT.type,
            eventNotifier = this@SettingViewModel
        ),

        // 이용약관
        SettingItemViewModel(
            InfomationsData.TERMS_OF_USE.title,
            InfomationsData.TERMS_OF_USE.type,
            eventNotifier = this@SettingViewModel
        ),

        // 개인정보 처리 방침
        SettingItemViewModel(
            InfomationsData.PRIVACY_POLICY.title,
            InfomationsData.PRIVACY_POLICY.type,
            eventNotifier = this@SettingViewModel
        ),

        // 테마설정
        SettingItemViewModel(
            InfomationsData.THEME.title,
            InfomationsData.THEME.type,
            eventNotifier = this@SettingViewModel
        ),

        // 알림설정
        SettingItemViewModel(
            InfomationsData.PUSH.title,
            InfomationsData.PUSH.type,
            eventNotifier = this@SettingViewModel
        ),
        // 릴리즈 노트
        SettingItemViewModel(
            InfomationsData.RELEASE_NOTE.title,
            InfomationsData.RELEASE_NOTE.type,
            eventNotifier = this@SettingViewModel
        ),

        // 오픈소스 라이브러리
        SettingItemViewModel(
            InfomationsData.OPEN_SOURCE_LIB.title,
            InfomationsData.OPEN_SOURCE_LIB.type,
            eventNotifier = this@SettingViewModel
        ),
        // 플레이 스토어
        SettingItemViewModel(
            InfomationsData.PLAY_STORE.title,
            InfomationsData.PLAY_STORE.type,
            eventNotifier = this@SettingViewModel
        ),
        // 앱 버전
        SettingItemViewModel(
            InfomationsData.APP_VERSION.title,
            InfomationsData.APP_VERSION.type,
            versionManager.version,
            eventNotifier = this@SettingViewModel
        ),
        // 로그아웃
        SettingItemViewModel(
            InfomationsData.LOGOUT.title,
            InfomationsData.LOGOUT.type,
            eventNotifier = this@SettingViewModel
        ),
        // 회원탈퇴
        SettingItemViewModel(
            InfomationsData.UNLINK.title,
            InfomationsData.UNLINK.type,
            eventNotifier = this@SettingViewModel
        )
    )
}