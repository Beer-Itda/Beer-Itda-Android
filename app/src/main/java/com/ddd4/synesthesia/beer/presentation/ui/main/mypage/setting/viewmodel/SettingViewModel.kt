package com.ddd4.synesthesia.beer.presentation.ui.main.mypage.setting.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.ddd4.synesthesia.beer.data.source.local.InfomationsData
import com.ddd4.synesthesia.beer.presentation.base.BaseViewModel
import com.ddd4.synesthesia.beer.presentation.ui.main.mypage.setting.item.SettingItemViewModel
import com.ddd4.synesthesia.beer.presentation.ui.main.mypage.setting.model.SettingActionEntity
import com.hjiee.core.manager.VersionManager
import com.hjiee.core.provider.SharedPreferenceProvider
import com.hjiee.domain.repository.LoginRepository
import com.hjiee.domain.usecase.login.LogoutUseCase
import kotlinx.coroutines.launch


class SettingViewModel @ViewModelInject constructor(
    private val versionManager: VersionManager,
    private val logoutUseCase: LogoutUseCase,
    private val preference: SharedPreferenceProvider
) : BaseViewModel() {

    val appVersion by lazy { versionManager.version }

    init {
        notifyActionEvent(SettingActionEntity.UpdateItem(generateInfoList()))
    }

//    fun logout() = loginRepository.logout {
//        if (it) {
//            SettingActionEntity.LogOut
//        }
//    }
//
//    fun unlink() = loginRepository.unlink {
//        if (it) {
//            SettingActionEntity.LogOut
//        }
//    }

    fun logOut() {
        viewModelScope.launch {
            runCatching {
                logoutUseCase.execute(
                    success = {
                        preference.clear()
                        notifyActionEvent(SettingActionEntity.LogOut)
                    }
                )
            }
        }
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