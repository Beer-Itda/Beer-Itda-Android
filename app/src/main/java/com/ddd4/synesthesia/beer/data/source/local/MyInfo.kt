package com.ddd4.synesthesia.beer.data.source.local

import com.ddd4.synesthesia.beer.presentation.base.event.SelectEventNotifier
import com.ddd4.synesthesia.beer.presentation.ui.mypage.entity.MyPageClickEntity

data class MyInfo(
    val title : String,
    val type : InfomationsType,
    val detailInfomation : String? = null,
    private val eventNotifier: SelectEventNotifier
) {
    fun clickItem() {
        eventNotifier.notifySelectEvent(MyPageClickEntity.SelectItem(this))
    }
}