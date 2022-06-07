package com.hjiee.presentation.ui.detail.item.related

import com.hjiee.presentation.ui.common.related.RelatedItemViewModel
import com.hjiee.presentation.ui.common.related.RelatedListAdapter
import com.hjiee.presentation.ui.common.related.RelatedType
import com.hjiee.presentation.ui.common.related.model.RelatedSelectEntity
import com.hjiee.presentation.ui.detail.item.IBeerDetailViewModel
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

    fun updateFavorite(beerId: Int) {
        relatedBeerList.map {
            if (it.beer.id == beerId) {
                it.beer.updateFavorite()
            }
        }
    }
}