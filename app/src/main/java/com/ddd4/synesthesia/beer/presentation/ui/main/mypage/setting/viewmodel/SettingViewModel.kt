package com.ddd4.synesthesia.beer.presentation.ui.main.mypage.setting.viewmodel

import androidx.lifecycle.viewModelScope
import com.ddd4.synesthesia.beer.ThemeHelper
import com.ddd4.synesthesia.beer.ThemeHelper.KEY_SELECTED_THEME_MODE
import com.ddd4.synesthesia.beer.ThemeMode
import com.ddd4.synesthesia.beer.data.source.local.InfomationsData
import com.ddd4.synesthesia.beer.presentation.base.BaseViewModel
import com.ddd4.synesthesia.beer.presentation.ui.main.mypage.setting.item.SettingItemViewModel
import com.ddd4.synesthesia.beer.presentation.ui.main.mypage.setting.model.SettingActionEntity
import com.ddd4.synesthesia.beer.presentation.ui.main.mypage.setting.view.SettingStringProvider
import com.hjiee.core.manager.VersionManager
import com.hjiee.core.provider.SharedPreferenceProvider
import com.hjiee.domain.usecase.login.LogoutUseCase
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
        // ????????????
        SettingItemViewModel(
            InfomationsData.NOTICE.title,
            InfomationsData.NOTICE.type,
            eventNotifier = this@SettingViewModel
        ),
        // ????????????
        SettingItemViewModel(
            InfomationsData.CONTACT.title,
            InfomationsData.CONTACT.type,
            eventNotifier = this@SettingViewModel
        ),

        // ????????????
        SettingItemViewModel(
            InfomationsData.TERMS_OF_USE.title,
            InfomationsData.TERMS_OF_USE.type,
            eventNotifier = this@SettingViewModel
        ),

        // ???????????? ?????? ??????
        SettingItemViewModel(
            InfomationsData.PRIVACY_POLICY.title,
            InfomationsData.PRIVACY_POLICY.type,
            eventNotifier = this@SettingViewModel
        ),

        // ????????????
        SettingItemViewModel(
            InfomationsData.THEME.title,
            InfomationsData.THEME.type,
            eventNotifier = this@SettingViewModel
        ),

        // ????????????
        SettingItemViewModel(
            InfomationsData.PUSH.title,
            InfomationsData.PUSH.type,
            eventNotifier = this@SettingViewModel
        ),
        // ????????? ??????
        SettingItemViewModel(
            InfomationsData.RELEASE_NOTE.title,
            InfomationsData.RELEASE_NOTE.type,
            eventNotifier = this@SettingViewModel
        ),

        // ???????????? ???????????????
        SettingItemViewModel(
            InfomationsData.OPEN_SOURCE_LIB.title,
            InfomationsData.OPEN_SOURCE_LIB.type,
            eventNotifier = this@SettingViewModel
        ),
        // ????????? ?????????
        SettingItemViewModel(
            InfomationsData.PLAY_STORE.title,
            InfomationsData.PLAY_STORE.type,
            eventNotifier = this@SettingViewModel
        ),
        // ??? ??????
        SettingItemViewModel(
            InfomationsData.APP_VERSION.title,
            InfomationsData.APP_VERSION.type,
            versionManager.version,
            eventNotifier = this@SettingViewModel
        ),
        // ????????????
        SettingItemViewModel(
            InfomationsData.LOGOUT.title,
            InfomationsData.LOGOUT.type,
            eventNotifier = this@SettingViewModel
        ),
        // ????????????
        SettingItemViewModel(
            InfomationsData.UNLINK.title,
            InfomationsData.UNLINK.type,
            eventNotifier = this@SettingViewModel
        )
    )
}