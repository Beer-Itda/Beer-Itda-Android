package com.ddd4.synesthesia.beer.presentation.ui.detail.item

import com.ddd4.synesthesia.beer.data.model.Beer
import com.ddd4.synesthesia.beer.presentation.base.event.SelectActionEventNotifier
import com.ddd4.synesthesia.beer.presentation.ui.common.beer.item.BeerItemViewModel
import com.ddd4.synesthesia.beer.presentation.ui.common.related.RelatedItemViewModel
import com.ddd4.synesthesia.beer.presentation.ui.common.review.ReviewItemViewModel

class BeerDetailItemViewModel(
    val beer: BeerItemViewModel,
    val rateOwnerBeerId: Int,
    val rateOwnerRatio: Float,
    val rateOwnerUserId: Int,
    val reviewOwnerBeer: Beer?,
    val myReview: ReviewItemViewModel?,
    val relatedAroma: List<RelatedItemViewModel>? = emptyList(),
    val relatedStyle: List<RelatedItemViewModel>? = emptyList(),
    val relatedRandom: List<RelatedItemViewModel>? = emptyList(),
    val eventNotifier: SelectActionEventNotifier
) {
}