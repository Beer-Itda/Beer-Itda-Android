package com.hjiee.presentation.ui.main.mypage.entity

import com.hjiee.core.event.entity.ItemClickEntity

sealed class MyPageClickEntity : ItemClickEntity() {
    object Modify : MyPageClickEntity()
    object Profile : MyPageClickEntity()
}