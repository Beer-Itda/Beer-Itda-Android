package com.ddd4.synesthesia.beer.presentation.ui.main.mypage.delete.entity

import com.hjiee.core.event.entity.ActionEntity

sealed class MyAccountDeleteActionEntity: ActionEntity() {
    object DeleteSuccess : MyAccountDeleteActionEntity()
    object DeleteFail : MyAccountDeleteActionEntity()
}