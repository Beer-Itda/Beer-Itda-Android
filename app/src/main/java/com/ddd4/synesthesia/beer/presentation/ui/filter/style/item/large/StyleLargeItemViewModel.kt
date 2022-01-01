package com.ddd4.synesthesia.beer.presentation.ui.filter.style.item.large

import androidx.databinding.ObservableBoolean
import com.ddd4.synesthesia.beer.presentation.ui.filter.style.item.middle.StyleMiddleItemViewModel
import com.hjiee.core.event.SelectActionEventNotifier

class StyleLargeItemViewModel(
    val bigName: String,
    val middleCategories: List<StyleMiddleItemViewModel>,
    val eventNotifier: SelectActionEventNotifier
) {

    val isSelected = ObservableBoolean(false)

}