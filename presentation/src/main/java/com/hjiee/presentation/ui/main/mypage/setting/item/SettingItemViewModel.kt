package com.hjiee.presentation.ui.main.mypage.setting.item

import com.hjiee.data.source.local.InfomationsType
import com.hjiee.presentation.ui.main.mypage.setting.model.SettingClickEntity
import com.hjiee.core.event.SelectEventNotifier

data class SettingItemViewModel(
    val title: String,
    val type: InfomationsType,
    val detailInfomation: String? = null,
    private val eventNotifier: SelectEventNotifier
) {
    fun clickItem() {
        eventNotifier.notifySelectEvent(SettingClickEntity.ItemClick(this))
    }
}