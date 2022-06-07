package com.hjiee.presentation.ui.main.search.item

import com.hjiee.presentation.ui.main.search.item.result.SearchItemViewModel
import com.hjiee.core.event.SelectEventNotifier
import com.hjiee.domain.entity.DomainEntity

object SearchMapper {

    fun getItems(
        searchText: String,
        items: List<DomainEntity.Beer>,
        eventNotifier: SelectEventNotifier
    ): List<ISearchViewModel> {
        return items.map {
            SearchItemViewModel(
                searchText = searchText,
                beer = it,
                eventNotifier = eventNotifier
            )
        }
    }
}