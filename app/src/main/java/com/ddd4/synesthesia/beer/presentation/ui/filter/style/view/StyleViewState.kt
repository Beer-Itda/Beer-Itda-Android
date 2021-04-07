package com.ddd4.synesthesia.beer.presentation.ui.filter.style.view

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.ddd4.synesthesia.beer.presentation.ui.common.filter.FilterViewState

class StyleViewState : FilterViewState() {

    val description = ObservableField<String>("")
}