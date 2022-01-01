package com.ddd4.synesthesia.beer.presentation.ui.filter.aroma.entity

import com.ddd4.synesthesia.beer.presentation.ui.filter.aroma.item.small.AromaItemViewModel
import com.hjiee.core.event.entity.ActionEntity

sealed class AromaActionEntity : ActionEntity() {
    class ShowToast(val message: String) : AromaActionEntity()
    class UpdateList(val list: List<AromaItemViewModel>) : AromaActionEntity()
    class UpdateSelectedList(val list: List<AromaItemViewModel>) : AromaActionEntity()
    class SelectDone(val items: List<AromaItemViewModel>) : AromaActionEntity()
}