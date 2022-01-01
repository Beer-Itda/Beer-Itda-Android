package com.ddd4.synesthesia.beer.presentation.ui.filter.style.entity

import com.ddd4.synesthesia.beer.presentation.ui.filter.style.item.large.StyleLargeItemViewModel
import com.ddd4.synesthesia.beer.presentation.ui.filter.style.item.middle.StyleMiddleItemViewModel
import com.ddd4.synesthesia.beer.presentation.ui.filter.style.item.small.StyleSmallItemViewModel
import com.hjiee.core.event.entity.ActionEntity

sealed class StyleActionEntity : ActionEntity() {
    class ShowToast(val message: String) : StyleActionEntity()
    class UpdateLarge(val styleLarge: List<StyleLargeItemViewModel>) : StyleActionEntity()
    class UpdateMiddle(val styleMiddle: List<StyleMiddleItemViewModel>) : StyleActionEntity()
    class UpdateSmall(val styleSmall: List<StyleSmallItemViewModel>) : StyleActionEntity()
    class UpdateSelectedStyleList(val style: List<StyleSmallItemViewModel>) :
        StyleActionEntity()
}