package com.ddd4.synesthesia.beer.presentation.ui.common.filter

import androidx.databinding.ObservableBoolean

open class FilterViewState {
    val isMaxSelected = ObservableBoolean(false)
    val isSelectedEmpty = ObservableBoolean(false)

    fun setIsMaxSelected(
        isEmpty: Boolean,
        size: Int,
        maxCount: Int
    ) {
        isSelectedEmpty.set(isEmpty)
        when {
            size >= maxCount -> {
                isMaxSelected.set(true)
            }
            size < maxCount -> {
                isMaxSelected.set(false)
            }
        }
    }
}