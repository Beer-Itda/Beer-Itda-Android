package com.ddd4.synesthesia.beer.presentation.ui.main.mypage.setting.model

import com.hjiee.core.event.entity.ItemClickEntity
import com.ddd4.synesthesia.beer.presentation.ui.main.mypage.setting.item.SettingItemViewModel

sealed class SettingClickEntity : ItemClickEntity() {
    class ItemClick(val item: SettingItemViewModel) : SettingClickEntity()
}