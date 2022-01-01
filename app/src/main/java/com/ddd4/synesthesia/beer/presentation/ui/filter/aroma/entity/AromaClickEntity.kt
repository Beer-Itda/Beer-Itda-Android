package com.ddd4.synesthesia.beer.presentation.ui.filter.aroma.entity

import com.ddd4.synesthesia.beer.presentation.ui.filter.aroma.item.small.AromaItemViewModel
import com.hjiee.core.event.entity.ItemClickEntity

sealed class AromaClickEntity : ItemClickEntity() {
    class AddAroma(val item: AromaItemViewModel) : AromaClickEntity()
    class DeleteAroma(val item: AromaItemViewModel) : AromaClickEntity()
}