package com.ddd4.synesthesia.beer.presentation.ui.filter.style.item.middle

import com.ddd4.synesthesia.beer.data.model.filter.style.StyleMiddleCategories
import com.ddd4.synesthesia.beer.presentation.base.event.SelectActionEventNotifier
import com.ddd4.synesthesia.beer.presentation.ui.filter.style.item.small.StyleSmallItemMapper

object StyleMiddleItemMapper {

    fun getCategories(
        largePosition: Int,
        list: List<StyleMiddleCategories>,
        eventNotifier: SelectActionEventNotifier
    ): List<StyleMiddleItemViewModel> {
        return list.mapIndexed { index, middle ->
            StyleMiddleItemViewModel(
                middleName = middle.middleName.orEmpty(),
                description = middle.description.orEmpty(),
                largePosition = largePosition,
                smallCategories = StyleSmallItemMapper.getCategories(
                    middleName = middle.middleName.orEmpty(),
                    smallCategories = middle.smallCategories.orEmpty(),
                    largePosition = largePosition,
                    middlePosition = index,
                    eventNotifier = eventNotifier
                ),
                eventNotifier = eventNotifier
            )
        }
    }
}