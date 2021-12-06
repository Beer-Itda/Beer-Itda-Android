package com.ddd4.synesthesia.beer.presentation.ui.detail.entity

import com.hjiee.core.event.entity.ActionEntity
import com.ddd4.synesthesia.beer.presentation.ui.detail.item.IBeerDetailViewModel

sealed class BeerDetailActionEntity : ActionEntity() {
    class UpdateUi(val items: List<IBeerDetailViewModel>) : BeerDetailActionEntity()
}