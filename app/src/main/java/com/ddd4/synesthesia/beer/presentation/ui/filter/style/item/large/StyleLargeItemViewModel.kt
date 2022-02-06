package com.ddd4.synesthesia.beer.presentation.ui.filter.style.item.large

import com.ddd4.synesthesia.beer.presentation.ui.filter.style.item.middle.StyleMiddleItemViewModel
import com.hjiee.core.event.SelectActionEventNotifier

class StyleLargeItemViewModel(
    val largeId: Int,
    val largeName: String,
    val middleCategories: List<StyleMiddleItemViewModel>,
    val eventNotifier: SelectActionEventNotifier
) {
}