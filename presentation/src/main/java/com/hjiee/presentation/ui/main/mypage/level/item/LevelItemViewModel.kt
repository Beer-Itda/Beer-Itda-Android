package com.hjiee.presentation.ui.main.mypage.level.item

import androidx.databinding.ObservableBoolean

class LevelItemViewModel(
    val id: Int,
    val level: String,
    val levelImage: String,
    val levelCount: Int
) {
    val isMyLevel = ObservableBoolean(false)
}