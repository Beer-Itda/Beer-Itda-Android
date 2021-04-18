package com.ddd4.synesthesia.beer.presentation.ui.filter.aroma.item.small

import androidx.databinding.ObservableBoolean
import com.ddd4.synesthesia.beer.presentation.base.event.SelectActionEventNotifier
import com.ddd4.synesthesia.beer.presentation.ui.filter.aroma.entity.AromaClickEntity

class AromaItemViewModel(
    val name: String,
    val isAll: Boolean,
    val position: Int
) {

    val isSelected = ObservableBoolean(false)
    var eventNotifier: SelectActionEventNotifier? = null

    fun clickAddItem() {
        eventNotifier?.notifySelectEvent(AromaClickEntity.AddAroma(this))
    }

    fun clickDeleteItem() {
        eventNotifier?.notifySelectEvent(AromaClickEntity.DeleteAroma(this))
    }

}