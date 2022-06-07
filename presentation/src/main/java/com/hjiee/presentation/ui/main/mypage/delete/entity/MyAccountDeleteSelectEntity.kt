package com.hjiee.presentation.ui.main.mypage.delete.entity

import com.hjiee.core.event.entity.ItemClickEntity

sealed class MyAccountDeleteSelectEntity : ItemClickEntity() {
    object Delete : MyAccountDeleteSelectEntity()
}