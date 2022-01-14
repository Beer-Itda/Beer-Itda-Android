package com.ddd4.synesthesia.beer.presentation.ui.filter.style.item.large

import com.ddd4.synesthesia.beer.presentation.ui.filter.style.item.middle.StyleMiddleItemMapper.getMiddle
import com.hjiee.core.event.SelectActionEventNotifier
import com.hjiee.domain.entity.DomainEntity

object StyleLargeItemMapper {

    fun List<DomainEntity.StyleLargeCategory>.getLarge(
        eventNotifier: SelectActionEventNotifier
    ): List<StyleLargeItemViewModel> {
        return mapIndexed { index, large ->
            StyleLargeItemViewModel(
                largeName = large.largeName,
                middleCategories = large.middleCategories.getMiddle(
                    largePosition = index,
                    eventNotifier = eventNotifier,
                ),
                eventNotifier = eventNotifier
            )
        }
    }
}