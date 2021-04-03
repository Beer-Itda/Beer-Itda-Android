package com.ddd4.synesthesia.beer.presentation.ui.filter.style.entity

import com.ddd4.synesthesia.beer.presentation.base.entity.ActionEntity
import com.ddd4.synesthesia.beer.presentation.ui.filter.style.item.middle.StyleMiddleItemViewModel
import com.ddd4.synesthesia.beer.presentation.ui.filter.style.item.small.StyleSmallItemViewModel

sealed class FilterActionEntity : ActionEntity() {
    class ShowToast(val message: String) : FilterActionEntity()
    class UpdateList(val styleMiddle: List<StyleMiddleItemViewModel>) : FilterActionEntity()
    class UpdateStyleSet(val styleSmall: List<StyleSmallItemViewModel>) : FilterActionEntity()
    class UpdateSelectedStyleList(val style: List<StyleSmallItemViewModel>) :
        FilterActionEntity()
}