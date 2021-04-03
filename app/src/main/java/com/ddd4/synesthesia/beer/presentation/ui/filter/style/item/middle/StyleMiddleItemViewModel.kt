package com.ddd4.synesthesia.beer.presentation.ui.filter.style.item.middle

import androidx.databinding.ObservableBoolean
import com.ddd4.synesthesia.beer.presentation.base.event.SelectActionEventNotifier
import com.ddd4.synesthesia.beer.presentation.ui.filter.style.entity.FilterClicklEntity
import com.ddd4.synesthesia.beer.presentation.ui.filter.style.item.small.StyleSmallItemViewModel

class StyleMiddleItemViewModel(
    val name: String,
    val description: String,
    val list: List<StyleSmallItemViewModel>,
    val eventNotifier: SelectActionEventNotifier
) {
    val isSelected = ObservableBoolean(false)

    fun onClick() {
        eventNotifier.notifySelectEvent(FilterClicklEntity.SelectMiddleCategory(this))
    }
}