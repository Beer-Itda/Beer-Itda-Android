package com.hjiee.presentation.ui.main.mypage.nickname.model

import com.hjiee.core.event.entity.ActionEntity

sealed class NickNameChangeActionEntity : ActionEntity() {
    object NickNameUpdated : NickNameChangeActionEntity()
    object HideKeyboard : NickNameChangeActionEntity()
}