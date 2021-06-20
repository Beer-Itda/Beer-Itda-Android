package com.ddd4.synesthesia.beer.presentation.ui.mypage.setting.model

import com.ddd4.synesthesia.beer.presentation.base.entity.ActionEntity
import com.ddd4.synesthesia.beer.presentation.ui.mypage.setting.item.SettingItemViewModel

sealed class SettingActionEntity : ActionEntity() {
    class UpdateItem(val items: List<SettingItemViewModel>) : SettingActionEntity()
    object LogOut : SettingActionEntity()
}