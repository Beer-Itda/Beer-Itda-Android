package com.ddd4.synesthesia.beer.presentation.ui.filter.style.item.small

import androidx.databinding.ObservableBoolean
import com.ddd4.synesthesia.beer.presentation.ui.filter.style.entity.StyleClicklEntity
import com.hjiee.core.event.SelectActionEventNotifier

class StyleSmallItemViewModel(
    val id: String,
    val middleName: String,
    val smallName: String,
    val isAll: Boolean,
    val largePosition: Int,
    val middlePosition: Int,
    val smallPosition: Int,
) {

    var eventNotifier: SelectActionEventNotifier? = null

    val isSelected = ObservableBoolean(false)

    fun getStyleName(): String {
        return if (isAll) {
            "$smallName ∙ $middleName"
        } else {
            smallName
        }
    }

    fun clickAddItem() {
        eventNotifier?.notifySelectEvent(StyleClicklEntity.AddStyle(this))
    }

    fun clickDeleteItem() {
        eventNotifier?.notifySelectEvent(StyleClicklEntity.DeleteStyle(this))
    }

}