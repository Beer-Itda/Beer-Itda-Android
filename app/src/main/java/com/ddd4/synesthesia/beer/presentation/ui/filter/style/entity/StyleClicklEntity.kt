package com.ddd4.synesthesia.beer.presentation.ui.filter.style.entity

import com.ddd4.synesthesia.beer.presentation.ui.filter.style.item.middle.StyleMiddleItemViewModel
import com.ddd4.synesthesia.beer.presentation.ui.filter.style.item.small.StyleSmallItemViewModel
import com.hjiee.core.event.entity.ItemClickEntity

sealed class StyleClicklEntity : ItemClickEntity() {
    class SelectMiddleCategory(val item: StyleMiddleItemViewModel) : StyleClicklEntity()
    class SelectStyleSet(val itemStyle: StyleSmallItemViewModel) : StyleClicklEntity()
    class AddStyle(val style: StyleSmallItemViewModel) : StyleClicklEntity()
    class DeleteStyle(val style: StyleSmallItemViewModel) : StyleClicklEntity()
    class SelectDone(val selectItem: List<StyleSmallItemViewModel>) : StyleClicklEntity()
}