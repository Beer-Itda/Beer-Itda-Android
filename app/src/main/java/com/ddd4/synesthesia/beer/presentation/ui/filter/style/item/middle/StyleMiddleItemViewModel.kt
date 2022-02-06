package com.ddd4.synesthesia.beer.presentation.ui.filter.style.item.middle

import androidx.databinding.ObservableBoolean
import com.ddd4.synesthesia.beer.presentation.ui.filter.style.entity.StyleClickEntity
import com.ddd4.synesthesia.beer.presentation.ui.filter.style.item.small.StyleSmallItemViewModel
import com.hjiee.core.event.SelectActionEventNotifier

class StyleMiddleItemViewModel(
    val parentId: Int,
    val middleId: Int,
    val middleName: String,
    val description: String,
    val largePosition: Int,
    val smallCategories: List<StyleSmallItemViewModel>,
    val eventNotifier: SelectActionEventNotifier
) {
    val isSelected = ObservableBoolean(false)

    fun onClick() {
        eventNotifier.notifySelectEvent(StyleClickEntity.SelectMiddleCategory(this))
    }
}
