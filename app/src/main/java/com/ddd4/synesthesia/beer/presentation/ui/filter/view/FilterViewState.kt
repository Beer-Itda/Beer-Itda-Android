package com.ddd4.synesthesia.beer.presentation.ui.filter.view

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField

class FilterViewState {
    val isMaxSelected = ObservableBoolean(false)
    val isSelectedEmpty = ObservableBoolean(false)
    val description = ObservableField<String>("")
}