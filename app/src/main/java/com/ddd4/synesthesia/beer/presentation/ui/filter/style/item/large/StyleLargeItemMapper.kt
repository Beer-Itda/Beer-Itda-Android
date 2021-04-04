package com.ddd4.synesthesia.beer.presentation.ui.filter.style.item.large

import com.ddd4.synesthesia.beer.data.model.filter.style.StyleLargeCategories
import com.ddd4.synesthesia.beer.presentation.base.event.SelectActionEventNotifier
import com.ddd4.synesthesia.beer.presentation.ui.filter.style.item.middle.StyleMiddleItemMapper

object StyleLargeItemMapper {

    fun getStyles(
        list: List<StyleLargeCategories>,
        eventNotifier: SelectActionEventNotifier
    ): List<StyleLargeItemViewModel> {
        return list.mapIndexed { index, style ->
            StyleLargeItemViewModel(
                bigName = style.bigName.orEmpty(),
                middleCategories = StyleMiddleItemMapper.getCategories(
                    largePosition = index,
                    list = style.styleMiddleCategories.orEmpty(),
                    eventNotifier = eventNotifier
                ),
                eventNotifier = eventNotifier
            )
        }
    }
}