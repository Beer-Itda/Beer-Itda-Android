package com.ddd4.synesthesia.beer.presentation.ui.main.mypage.setting.model

import com.ddd4.synesthesia.beer.presentation.ui.main.mypage.setting.item.SettingItemViewModel
import com.hjiee.core.event.entity.ActionEntity

sealed class SettingActionEntity : ActionEntity() {
    class UpdateItem(val items: List<SettingItemViewModel>) : SettingActionEntity()
    object LogOut : SettingActionEntity()
}