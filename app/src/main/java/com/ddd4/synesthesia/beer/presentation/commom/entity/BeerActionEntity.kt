package com.ddd4.synesthesia.beer.presentation.commom.entity

import com.hjiee.core.event.entity.ActionEntity

sealed class BeerActionEntity : ActionEntity() {
    object Refresh : BeerActionEntity()
}