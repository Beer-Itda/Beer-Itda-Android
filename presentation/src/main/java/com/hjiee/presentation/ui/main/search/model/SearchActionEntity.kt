package com.hjiee.presentation.ui.main.search.model

import com.hjiee.presentation.ui.main.search.item.ISearchViewModel
import com.hjiee.core.event.entity.ActionEntity

sealed class SearchActionEntity : ActionEntity() {
    class UpdateList(val items: List<ISearchViewModel>) : SearchActionEntity()
    object Refresh : SearchActionEntity()
}