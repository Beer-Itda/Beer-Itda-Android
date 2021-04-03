package com.ddd4.synesthesia.beer.presentation.ui.filter.style.item.small

import androidx.databinding.ObservableBoolean
import com.ddd4.synesthesia.beer.presentation.base.event.SelectActionEventNotifier
import com.ddd4.synesthesia.beer.presentation.ui.filter.style.entity.FilterClicklEntity

class StyleSmallItemViewModel(
    val title: String,
    val name: String,
    val isAll: Boolean,
    val eventNotifier: SelectActionEventNotifier
) {

    val isSelected = ObservableBoolean(false)

    fun getStyleName(): String {
        return if (isAll) {
            "$name ∙ $title"
        } else {
            name
        }
    }

    fun clickAddItem() {
        eventNotifier.notifySelectEvent(FilterClicklEntity.AddStyle(this))
    }

    fun clickDeleteItem() {
        eventNotifier.notifySelectEvent(FilterClicklEntity.DeleteStyle(this))
    }

}