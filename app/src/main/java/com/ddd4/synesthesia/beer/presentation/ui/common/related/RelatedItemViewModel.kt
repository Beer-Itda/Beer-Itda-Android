package com.ddd4.synesthesia.beer.presentation.ui.common.related

import com.ddd4.synesthesia.beer.presentation.base.event.SelectEventNotifier
import com.ddd4.synesthesia.beer.presentation.ui.common.beer.item.BeerItemViewModel

class RelatedItemViewModel(
    val beer: BeerItemViewModel,
    val abv: Float,
    val aroma: List<String>,
    val beerStyle: String,
    val brewery: String,
    val country: String,
    val id: Int,
    val name: String,
    val rateAvg: Float,
    val thumbnailImage: String,
    val type: RelatedType,
    val eventNotifier: SelectEventNotifier
) : IRelatedListViewModel {

}

enum class RelatedType {
    AROMA,
    STYLE,
    RANDOM
}
