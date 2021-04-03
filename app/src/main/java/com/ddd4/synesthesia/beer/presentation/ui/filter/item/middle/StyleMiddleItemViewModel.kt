package com.ddd4.synesthesia.beer.presentation.ui.filter.item.middle

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.ddd4.synesthesia.beer.presentation.base.event.SelectActionEventNotifier
import com.ddd4.synesthesia.beer.presentation.ui.filter.entity.FilterClicklEntity
import com.ddd4.synesthesia.beer.presentation.ui.filter.item.small.StyleSmallItemViewModel

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