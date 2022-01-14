package com.ddd4.synesthesia.beer.presentation.ui.filter.style.item.middle

import com.ddd4.synesthesia.beer.presentation.ui.filter.style.item.small.StyleSmallItemMapper.getSmall
import com.hjiee.core.event.SelectActionEventNotifier
import com.hjiee.domain.entity.DomainEntity

object StyleMiddleItemMapper {

    fun List<DomainEntity.StyleMiddleCategory>.getMiddle(
        largePosition: Int,
        eventNotifier: SelectActionEventNotifier
    ): List<StyleMiddleItemViewModel> {
        return mapIndexed { index, middle ->
            StyleMiddleItemViewModel(
                middleName = middle.middleName,
                description = middle.description,
                largePosition = largePosition,
                smallCategories = middle.smallCategories.getSmall(
                    middleName = middle.middleName,
                    largePosition = largePosition,
                    middlePosition = index,
                    eventNotifier = eventNotifier
                ),
                eventNotifier = eventNotifier
            )
        }
    }
}