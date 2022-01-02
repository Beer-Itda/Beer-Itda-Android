package com.ddd4.synesthesia.beer.presentation.ui.detail.entity

import com.ddd4.synesthesia.beer.presentation.ui.detail.item.IBeerDetailViewModel
import com.hjiee.core.event.entity.ActionEntity

sealed class BeerDetailActionEntity : ActionEntity() {
    class UpdateUi(val items: List<IBeerDetailViewModel>) : BeerDetailActionEntity()
}