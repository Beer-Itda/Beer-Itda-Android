package com.ddd4.synesthesia.beer.presentation.ui.filter.style.item.small

import com.ddd4.synesthesia.beer.presentation.base.event.SelectActionEventNotifier

object StyleSmallItemMapper {

    fun getCategories(
        middleName: String,
        largePosition: Int,
        middlePosition: Int,
        smallCategories: List<String>,
        eventNotifier: SelectActionEventNotifier
    ): List<StyleSmallItemViewModel> {
        val items = smallCategories.map {
            StyleSmallItemViewModel(
                middleName = middleName,
                smallName = it,
                largePosition = largePosition,
                middlePosition = middlePosition,
                isAll = false,
                eventNotifier = eventNotifier
            )
        }.toMutableList()

        items.add(
            0,
            StyleSmallItemViewModel(
                middleName = middleName,
                smallName = "전체선택",
                largePosition = largePosition,
                middlePosition = middlePosition,
                isAll = true,
                eventNotifier = eventNotifier
            )
        )
        return items
    }
}