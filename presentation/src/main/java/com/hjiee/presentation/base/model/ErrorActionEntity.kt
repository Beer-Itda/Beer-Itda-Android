package com.hjiee.presentation.base.model

import com.hjiee.core.event.entity.ActionEntity

sealed class ErrorActionEntity : ActionEntity() {
    class ShowErrorMessage(val message: String) : ErrorActionEntity()
}