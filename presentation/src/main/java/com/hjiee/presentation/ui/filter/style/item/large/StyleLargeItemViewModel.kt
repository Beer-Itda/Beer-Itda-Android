package com.hjiee.presentation.ui.filter.style.item.large

import com.hjiee.presentation.ui.filter.style.item.middle.StyleMiddleItemViewModel
import com.hjiee.core.event.SelectActionEventNotifier

class StyleLargeItemViewModel(
    val largeId: Int,
    val largeName: String,
    val middleCategories: List<StyleMiddleItemViewModel>,
    val eventNotifier: SelectActionEventNotifier
) {
}