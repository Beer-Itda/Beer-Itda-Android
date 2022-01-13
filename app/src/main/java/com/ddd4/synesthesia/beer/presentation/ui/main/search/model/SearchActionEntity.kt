package com.ddd4.synesthesia.beer.presentation.ui.main.search.model

import com.ddd4.synesthesia.beer.presentation.ui.main.search.item.ISearchViewModel
import com.hjiee.core.event.entity.ActionEntity

sealed class SearchActionEntity : ActionEntity() {
    class UpdateList(val items: List<ISearchViewModel>) : SearchActionEntity()
    object Refresh : SearchActionEntity()
}