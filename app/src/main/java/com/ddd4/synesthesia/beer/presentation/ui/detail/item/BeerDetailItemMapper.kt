package com.ddd4.synesthesia.beer.presentation.ui.detail.item

import com.ddd4.synesthesia.beer.data.model.Response
import com.ddd4.synesthesia.beer.ext.orDefault
import com.ddd4.synesthesia.beer.ext.orZero
import com.ddd4.synesthesia.beer.presentation.base.event.SelectActionEventNotifier
import com.ddd4.synesthesia.beer.presentation.ui.common.beer.item.BeerItemViewModelMapper.getBeerItemViewModel
import com.ddd4.synesthesia.beer.presentation.ui.common.related.RelatedType
import com.ddd4.synesthesia.beer.presentation.ui.common.review.ReviewItemViewModel
import com.ddd4.synesthesia.beer.presentation.ui.detail.item.info.BeerDetailInfoItemViewModelMapper.getInfo
import com.ddd4.synesthesia.beer.presentation.ui.detail.item.related.BeerDetailRelatedMapper.convertRelatedBeer
import com.ddd4.synesthesia.beer.presentation.ui.detail.item.related.BeerDetailRelatedMapper.getRelatedListItemViewModel
import com.ddd4.synesthesia.beer.presentation.ui.detail.item.review.BeerDetailReviewMapper.getReviewListItemViewModel

object BeerDetailItemMapper {

    fun Response?.getBeerDetailItemViewModel(eventNotifier: SelectActionEventNotifier): List<IBeerDetailViewModel> {
        val beer = this?.beer?.run {
            BeerDetailItemViewModel(
                beer = getBeerItemViewModel(eventNotifier),
                rateOwnerBeerId = rateOwner?.beerId.orZero(),
                rateOwnerRatio = rateOwner?.ratio.orZero(),
                rateOwnerUserId = rateOwner?.userId.orZero(),
                reviewOwnerBeer = reviewOwner?.beer,
                myReview = reviewOwner?.let {
                    ReviewItemViewModel(
                        reviewId = it.userId.orZero(),
                        writerNickName = it.nickname.orEmpty(),
                        ratio = it.ratio.orDefault(0.5f),
                        content = it.content.orEmpty(),
                        createdAt = "",
                    )
                } ?: kotlin.run { null },
                relatedAroma = relatedBeers?.convertRelatedBeer(
                    type = RelatedType.AROMA,
                    eventNotifier = eventNotifier
                ),
                relatedStyle = relatedBeers?.convertRelatedBeer(
                    type = RelatedType.STYLE,
                    eventNotifier = eventNotifier
                ),
                relatedRandom = relatedBeers?.convertRelatedBeer(
                    type = RelatedType.RANDOM,
                    eventNotifier = eventNotifier
                ),
            )
        }

        return mutableListOf<IBeerDetailViewModel>().apply {
            beer?.let {
                add(it.getInfo())
                add(it.getReviewListItemViewModel(eventNotifier))
                add(it.getRelatedListItemViewModel(eventNotifier, RelatedType.AROMA))
                add(it.getRelatedListItemViewModel(eventNotifier, RelatedType.STYLE))
                add(it.getRelatedListItemViewModel(eventNotifier,RelatedType.RANDOM))

//                if(it.getRelatedListItemViewModel(eventNotifier, RelatedType.AROMA).relatedBeerList.isNotEmpty()) {
//                    add(it.getRelatedListItemViewModel(eventNotifier, RelatedType.AROMA))
//                }
//                if(it.getRelatedListItemViewModel(eventNotifier, RelatedType.STYLE).relatedBeerList.isNotEmpty()) {
//                    add(it.getRelatedListItemViewModel(eventNotifier, RelatedType.STYLE))
//                }
//                if(it.getRelatedListItemViewModel(eventNotifier, RelatedType.RANDOM).relatedBeerList.isNotEmpty()) {
//                    add(it.getRelatedListItemViewModel(eventNotifier,RelatedType.RANDOM))
//                }
            }
        }
    }
}