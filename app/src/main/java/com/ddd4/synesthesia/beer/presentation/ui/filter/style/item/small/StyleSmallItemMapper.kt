package com.ddd4.synesthesia.beer.presentation.ui.filter.style.item.small

import com.hjiee.core.event.SelectActionEventNotifier

object StyleSmallItemMapper {

    fun getCategories(
        middleName: String,
        largePosition: Int,
        middlePosition: Int,
        smallCategories: List<String>,
        eventNotifier: SelectActionEventNotifier
    ): List<StyleSmallItemViewModel> {
        val items = smallCategories.mapIndexed { smallPosition, small ->
            StyleSmallItemViewModel(
                id = "%03d".format(largePosition) + "%03d".format(middlePosition) + "%03d".format(smallPosition + 1),
                middleName = middleName,
                smallName = small,
                largePosition = largePosition,
                middlePosition = middlePosition,
                smallPosition = smallPosition + 1,
                isAll = false
            ).apply {
                this.eventNotifier = eventNotifier
            }
        }.toMutableList()

        items.add(
            0,
            StyleSmallItemViewModel(
                id = "%03d".format(largePosition) + "%03d".format(middlePosition) + "%03d".format(0),
                middleName = middleName,
                smallName = "전체선택",
                largePosition = largePosition,
                middlePosition = middlePosition,
                smallPosition = 0,
                isAll = true
            ).apply {
                this.eventNotifier = eventNotifier
            }
        )
        return items
    }
}