package com.ddd4.synesthesia.beer.presentation.ui.mypage.entity

import com.ddd4.synesthesia.beer.data.source.local.MyInfo
import com.ddd4.synesthesia.beer.presentation.base.entity.ItemClickEntity

sealed class MyPageClickEntity : ItemClickEntity() {
    object Modify : MyPageClickEntity()
    class SelectItem(val info : MyInfo) : MyPageClickEntity()
}