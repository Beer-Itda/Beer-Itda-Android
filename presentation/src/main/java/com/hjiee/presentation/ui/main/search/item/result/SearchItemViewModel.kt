package com.hjiee.presentation.ui.main.search.item.result

import com.hjiee.presentation.ui.main.search.item.ISearchViewModel
import com.hjiee.presentation.ui.main.search.model.SearchSelectEvent
import com.hjiee.core.event.SelectEventNotifier
import com.hjiee.domain.entity.DomainEntity

class SearchItemViewModel(
    val searchText: String,
    val beer: DomainEntity.Beer,
    private val eventNotifier: SelectEventNotifier
) : ISearchViewModel {

    fun clickItem() {
        eventNotifier.notifySelectEvent(SearchSelectEvent.SelectItem(beer))
    }
}