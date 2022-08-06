package com.hjiee.presentation.ui.filter.style.view

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.hjiee.presentation.ui.common.filter.FilterViewState

class StyleViewState : FilterViewState() {

    val description = ObservableField<String>("")
    val isMaxParentCount = ObservableBoolean(false)
    val isMaxChildCount = ObservableBoolean(false)
}