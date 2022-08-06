package com.hjiee.presentation.ui.filter.aroma.item.small

import com.hjiee.core.event.SelectActionEventNotifier
import com.hjiee.domain.entity.DomainEntity
import com.hjiee.domain.entity.request.RequestSelectedAroma

object AromaItemMapper {

    fun List<DomainEntity.Aroma>?.getItem(
        eventNotifier: SelectActionEventNotifier
    ): List<AromaItemViewModel> {
        return getItems(this.orEmpty(), eventNotifier)
    }

    private fun getItems(
        items: List<DomainEntity.Aroma>,
        eventNotifier: SelectActionEventNotifier
    ): List<AromaItemViewModel> {
        return items.mapIndexed { index, item ->
            AromaItemViewModel(
                id = item.id,
                name = item.name,
                isAll = index == 0,
                position = index + 1,
                eventNotifier = eventNotifier
            ).apply {
                isSelected.set(item.isSelected)
            }
        }
    }

    fun List<AromaItemViewModel>.idList() : RequestSelectedAroma {
        return RequestSelectedAroma(
            aromaIds = map { it.id }.toIntArray()
        )
    }
}