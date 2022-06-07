package com.hjiee.presentation.ui.main.mypage.setting.model

import com.hjiee.presentation.ui.main.mypage.setting.item.SettingItemViewModel
import com.hjiee.core.event.entity.ItemClickEntity

sealed class SettingClickEntity : ItemClickEntity() {
    class ItemClick(val item: SettingItemViewModel) : SettingClickEntity()
}