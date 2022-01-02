package com.ddd4.synesthesia.beer.presentation.ui.detail.entity

import com.hjiee.core.event.entity.ActionEntity

sealed class ReviewWriteActionEntity : ActionEntity() {
    object Success : ReviewWriteActionEntity()
}