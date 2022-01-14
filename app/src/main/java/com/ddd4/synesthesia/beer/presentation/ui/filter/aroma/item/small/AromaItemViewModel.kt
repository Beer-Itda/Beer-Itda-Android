package com.ddd4.synesthesia.beer.presentation.ui.filter.aroma.item.small

import androidx.databinding.ObservableBoolean
import com.ddd4.synesthesia.beer.presentation.ui.filter.aroma.entity.AromaClickEntity
import com.hjiee.core.event.SelectActionEventNotifier

class AromaItemViewModel(
    val id: Int,
    val name: String,
    val isAll: Boolean,
    val position: Int,
    private val eventNotifier: SelectActionEventNotifier
) {

    val isSelected = ObservableBoolean(false)

    fun clickAddItem() {
        eventNotifier.notifySelectEvent(AromaClickEntity.AddAroma(this))
    }

    fun clickDeleteItem() {
        eventNotifier.notifySelectEvent(AromaClickEntity.DeleteAroma(this))
    }

}