package com.ddd4.synesthesia.beer.presentation.ui.main.mypage.entity

import com.ddd4.synesthesia.beer.presentation.base.entity.ItemClickEntity

sealed class MyPageClickEntity : ItemClickEntity() {
    object Modify : MyPageClickEntity()
}