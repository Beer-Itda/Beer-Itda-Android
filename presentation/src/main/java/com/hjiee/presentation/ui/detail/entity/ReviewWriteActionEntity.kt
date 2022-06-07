package com.hjiee.presentation.ui.detail.entity

import com.hjiee.core.event.entity.ActionEntity

sealed class ReviewWriteActionEntity : ActionEntity() {
    object Success : ReviewWriteActionEntity()
}