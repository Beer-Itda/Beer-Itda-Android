package com.ddd4.synesthesia.beer.presentation.ui.filter.style.item.small

import com.hjiee.core.event.SelectActionEventNotifier
import com.hjiee.domain.entity.DomainEntity

object StyleSmallItemMapper {

    fun List<DomainEntity.StyleSmallCategory>.getSmall(
        middleName: String,
        largePosition: Int,
        middlePosition: Int,
        eventNotifier: SelectActionEventNotifier
    ): List<StyleSmallItemViewModel> {
        val items = mutableListOf<StyleSmallItemViewModel>()
        items.add(
            getAllSelectCategory(
                largePosition = largePosition,
                middlePosition = middlePosition,
                middleName = middleName,
                eventNotifier = eventNotifier
            )
        )
        items.addAll(
            getSmallCategory(
                largePosition = largePosition,
                middlePosition = middlePosition,
                middleName = middleName,
                eventNotifier = eventNotifier
            )
        )
        return items
    }

    private fun List<DomainEntity.StyleSmallCategory>.getSmallCategory(
        largePosition: Int,
        middlePosition: Int,
        middleName: String,
        eventNotifier: SelectActionEventNotifier
    ): List<StyleSmallItemViewModel> {
        return mapIndexed { smallPosition, small ->
            StyleSmallItemViewModel(
                id = "%03d".format(largePosition) + "%03d".format(middlePosition) + "%03d".format(
                    smallPosition + 1
                ),
                middleName = middleName,
                smallName = small.smallName,
                largePosition = largePosition,
                middlePosition = middlePosition,
                smallPosition = smallPosition + 1,
                isAll = false,
                eventNotifier = eventNotifier
            )
        }
    }

    private fun getAllSelectCategory(
        largePosition: Int,
        middlePosition: Int,
        middleName: String,
        eventNotifier: SelectActionEventNotifier
    ): StyleSmallItemViewModel {
        return StyleSmallItemViewModel(
            id = "%03d".format(largePosition) + "%03d".format(middlePosition) + "%03d".format(0),
            middleName = middleName,
            smallName = "전체선택",
            largePosition = largePosition,
            middlePosition = middlePosition,
            smallPosition = 0,
            isAll = true,
            eventNotifier = eventNotifier
        )
    }
}