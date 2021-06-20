package com.ddd4.synesthesia.beer.presentation.ui.mypage.setting.model

import com.ddd4.synesthesia.beer.presentation.base.entity.ItemClickEntity
import com.ddd4.synesthesia.beer.presentation.ui.mypage.setting.item.SettingItemViewModel

sealed class SettingClickEntity : ItemClickEntity() {
    class ItemClick(val item: SettingItemViewModel) : SettingClickEntity()
}