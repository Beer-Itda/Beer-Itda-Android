package com.hjiee.presentation.ui.filter.style.entity

import com.hjiee.presentation.ui.filter.style.item.large.StyleLargeItemViewModel
import com.hjiee.presentation.ui.filter.style.item.middle.StyleMiddleItemViewModel
import com.hjiee.presentation.ui.filter.style.item.small.StyleSmallItemViewModel
import com.hjiee.core.event.entity.ActionEntity

sealed class StyleActionEntity : ActionEntity() {
    class ShowToast(val message: String) : StyleActionEntity()
    class UpdateLarge(val style: List<StyleLargeItemViewModel>) : StyleActionEntity()
    class UpdateMiddle(val style: List<StyleMiddleItemViewModel>) : StyleActionEntity()
    class UpdateSmall(val style: List<StyleSmallItemViewModel>) : StyleActionEntity()
    class UpdateSelectedStyleList(val style: List<StyleSmallItemViewModel>) :
        StyleActionEntity()
}