package com.ddd4.synesthesia.beer.presentation.commom.entity

import com.ddd4.synesthesia.beer.presentation.base.entity.ActionEntity

sealed class BeerActionEntity : ActionEntity() {
    object Refresh : BeerActionEntity()
}