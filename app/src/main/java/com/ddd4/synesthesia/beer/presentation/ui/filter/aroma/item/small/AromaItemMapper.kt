package com.ddd4.synesthesia.beer.presentation.ui.filter.aroma.item.small

import com.hjiee.core.event.SelectActionEventNotifier

object AromaItemMapper {

    fun get(
        items: List<String>,
        eventNotifier: SelectActionEventNotifier
    ): List<AromaItemViewModel> {
        val list = items.mapIndexed { index, item ->
            AromaItemViewModel(
                name = item,
                isAll = false,
                position = index + 1
            ).apply {
                this.eventNotifier = eventNotifier
            }
        }.toMutableList()

        list.add(
            0, AromaItemViewModel(
                name = "전체선택",
                isAll = true,
                position = 0
            ).apply {
                this.eventNotifier = eventNotifier
            }
        )
        return list
    }
}