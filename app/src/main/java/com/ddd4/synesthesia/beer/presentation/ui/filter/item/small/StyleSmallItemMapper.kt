package com.ddd4.synesthesia.beer.presentation.ui.filter.item.small

import com.ddd4.synesthesia.beer.presentation.base.event.SelectActionEventNotifier

object StyleSmallItemMapper {
    fun get(
        title: String,
        list: List<String>,
        eventNotifier: SelectActionEventNotifier
    ): List<StyleSmallItemViewModel> {
        val items = list.map {
            StyleSmallItemViewModel(
                title = title,
                name = it,
                isAll = false,
                eventNotifier = eventNotifier
            )
        }.toMutableList()

        items.add(
            0,
            StyleSmallItemViewModel(
                title = title,
                name = "전체선택",
                isAll = true,
                eventNotifier = eventNotifier
            )
        )
        return items
    }
}