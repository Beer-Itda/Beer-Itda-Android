package com.ddd4.synesthesia.beer.presentation.ui.main.search.item

import com.ddd4.synesthesia.beer.presentation.ui.main.search.item.result.SearchItemViewModel
import com.hjiee.core.event.SelectEventNotifier
import com.hjiee.domain.entity.DomainEntity

object SearchMapper {

    fun getItems(
        items: List<DomainEntity.Beer>,
        eventNotifier: SelectEventNotifier
    ): List<ISearchViewModel> {
        return items.map {
            SearchItemViewModel(
                beer = it,
                eventNotifier = eventNotifier
            )
        }
    }
}