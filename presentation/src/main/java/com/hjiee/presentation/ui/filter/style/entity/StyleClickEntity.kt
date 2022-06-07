package com.hjiee.presentation.ui.filter.style.entity

import com.hjiee.presentation.ui.filter.style.item.middle.StyleMiddleItemViewModel
import com.hjiee.presentation.ui.filter.style.item.small.StyleSmallItemViewModel
import com.hjiee.core.event.entity.ItemClickEntity

sealed class StyleClickEntity : ItemClickEntity() {
    class SelectMiddleCategory(val item: StyleMiddleItemViewModel) : StyleClickEntity()
    class SelectStyleSet(val itemStyle: StyleSmallItemViewModel) : StyleClickEntity()
    class AddStyle(val style: StyleSmallItemViewModel) : StyleClickEntity()
    class DeleteStyle(val style: StyleSmallItemViewModel) : StyleClickEntity()
    object Skip : StyleClickEntity()
    object SelectDone : StyleClickEntity()
}