package com.ddd4.synesthesia.beer.presentation.ui.login.model

import com.hjiee.core.event.entity.ActionEntity

sealed class LoginActionEntity: ActionEntity() {
    object SuccessLogin : LoginActionEntity()
}