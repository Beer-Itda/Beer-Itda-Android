package com.ddd4.synesthesia.beer.presentation.ui.filter.aroma.item.small

import com.ddd4.synesthesia.beer.presentation.base.event.SelectActionEventNotifier

object AromaItemMapper {

    fun get(
        items: List<String>,
        eventNotifier: SelectActionEventNotifier
    ): List<AromaItemViewModel> {
        val list = items.map {
            AromaItemViewModel(
                name = it,
                isAll = false,
                eventNotifier = eventNotifier
            )
        }.toMutableList()

        list.add(
            0, AromaItemViewModel(
                name = "전체선택",
                isAll = true,
                eventNotifier = eventNotifier
            )
        )
        return list
    }
}