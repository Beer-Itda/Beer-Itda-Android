package com.ddd4.synesthesia.beer.presentation.ui.detail.item.related

import com.ddd4.synesthesia.beer.presentation.ui.common.related.RelatedItemViewModel
import com.ddd4.synesthesia.beer.presentation.ui.common.related.RelatedListAdapter
import com.ddd4.synesthesia.beer.presentation.ui.common.related.RelatedType
import com.ddd4.synesthesia.beer.presentation.ui.common.related.model.RelatedSelectEntity
import com.ddd4.synesthesia.beer.presentation.ui.detail.item.IBeerDetailViewModel
import com.hjiee.core.event.SelectEventNotifier

class BeerDetailRelatedListViewModel(
    val title: String,
    val type: RelatedType,
    val relatedBeerList: List<RelatedItemViewModel>,
    val eventNotifier: SelectEventNotifier
) : IBeerDetailViewModel {

    val adapter = RelatedListAdapter()

    init {
        adapter.clear()
        adapter.addAll(relatedBeerList)
    }

    fun clickShowMore() {
        eventNotifier.notifySelectEvent(RelatedSelectEntity.SelectTitle(type))
    }
}