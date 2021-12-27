package com.ddd4.synesthesia.beer.presentation.ui.common.related

import com.hjiee.core.event.SelectEventNotifier
import com.ddd4.synesthesia.beer.presentation.ui.common.beer.item.BeerItemViewModel
import com.hjiee.domain.entity.DomainEntity

class RelatedItemViewModel(
    val beer: BeerItemViewModel,
    val type: RelatedType,
    val eventNotifier: SelectEventNotifier
) : IRelatedListViewModel {

}

enum class RelatedType {
    AROMA,
    STYLE,
    RANDOM
}
