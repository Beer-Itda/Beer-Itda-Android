package com.hjiee.presentation.commom.entity

import com.hjiee.core.event.entity.ActionEntity

sealed class BeerActionEntity : ActionEntity() {
    object Refresh : BeerActionEntity()
}