package com.ddd4.synesthesia.beer.presentation.ui.common.beer.item

import com.ddd4.synesthesia.beer.data.model.Beer
import com.ddd4.synesthesia.beer.ext.orFalse
import com.ddd4.synesthesia.beer.ext.orZero
import com.ddd4.synesthesia.beer.presentation.base.event.SelectActionEventNotifier

object BeerItemViewModelMapper {

    fun getMapper(beer: Beer,eventNotifier: SelectActionEventNotifier): BeerItemViewModel {
        return BeerItemViewModel(
            abv = beer.abv.orZero(),
            aromas = beer.aromas.orEmpty(),
            beerStyle = beer.beerStyle.orEmpty(),
            brewery = beer.brewery.orEmpty(),
            country = beer.country.orEmpty(),
            id = beer.id.orZero(),
            imageUrl = beer.imageUrl.orEmpty(),
            name = beer.name.orEmpty(),
            rateAvg = beer.rateAvg.orZero(),
            rateOwnerBeerId = beer.rateOwner?.beerId.orZero(),
            rateOwnerRatio = beer.rateOwner?.ratio.orZero(),
            rateOwnerUserId = beer.rateOwner?.userId.orZero(),
            reviews = beer.reviews.orEmpty(),
            thumbnailImage = beer.thumbnailImage.orEmpty(),
            reviewOwnerBeer = beer.reviewOwner?.beer,
            reviewOwnerContent = beer.reviewOwner?.content.orEmpty(),
            reviewOwnerNickname = beer.reviewOwner?.nickname.orEmpty(),
            reviewOwnerRatio = beer.reviewOwner?.ratio.orZero(),
            reviewOwnerUserId = beer.reviewOwner?.userId.orZero(),
            beer.favoriteFlag.orFalse()
        ).apply {
            this.eventNotifier = eventNotifier
        }
    }
}