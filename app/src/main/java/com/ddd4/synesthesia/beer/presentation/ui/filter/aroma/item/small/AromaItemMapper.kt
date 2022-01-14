package com.ddd4.synesthesia.beer.presentation.ui.filter.aroma.item.small

import com.hjiee.core.event.SelectActionEventNotifier
import com.hjiee.domain.entity.DomainEntity

object AromaItemMapper {

    fun List<DomainEntity.Aroma>?.getItem(
        eventNotifier: SelectActionEventNotifier
    ): List<AromaItemViewModel> {
        val list = mutableListOf<AromaItemViewModel>()

        list.add(getAllSelectItem(eventNotifier))
        list.addAll(getItems(this.orEmpty(), eventNotifier))

        return list
    }

    private fun getAllSelectItem(eventNotifier: SelectActionEventNotifier): AromaItemViewModel {
        return AromaItemViewModel(
            id = -1,
            name = "전체선택",
            isAll = true,
            position = 0,
            eventNotifier = eventNotifier
        )
    }

    private fun getItems(
        items: List<DomainEntity.Aroma>,
        eventNotifier: SelectActionEventNotifier
    ): List<AromaItemViewModel> {
        return items.mapIndexed { index, item ->
            AromaItemViewModel(
                id = item.id,
                name = item.name,
                isAll = false,
                position = index + 1,
                eventNotifier = eventNotifier
            )
        }

    }
}