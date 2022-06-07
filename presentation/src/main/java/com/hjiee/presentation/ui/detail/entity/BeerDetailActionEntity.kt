package com.hjiee.presentation.ui.detail.entity

import com.hjiee.presentation.ui.detail.item.IBeerDetailViewModel
import com.hjiee.core.event.entity.ActionEntity

sealed class BeerDetailActionEntity : ActionEntity() {
    class UpdateUi(val items: List<IBeerDetailViewModel>) : BeerDetailActionEntity()
    class Toast(val message: String) : BeerDetailActionEntity()
}