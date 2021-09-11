package com.ddd4.synesthesia.beer.presentation.ui.common.beer.item

import com.ddd4.synesthesia.beer.data.model.Beer
import com.ddd4.synesthesia.beer.ext.orDefault
import com.ddd4.synesthesia.beer.ext.orFalse
import com.ddd4.synesthesia.beer.ext.orZero
import com.ddd4.synesthesia.beer.presentation.base.event.SelectActionEventNotifier

object BeerItemViewModelMapper {

    fun Beer?.getBeerItemViewModel(eventNotifier: SelectActionEventNotifier): BeerItemViewModel {
        return if (this == null) {
            getNullBeerItem()
        } else {
            BeerItemViewModel(
                abv = abv.orZero(),
                aromas = aromas.orEmpty(),
                beerStyle = beerStyle.orEmpty(),
                brewery = brewery.orEmpty(),
                country = country.orEmpty(),
                id = id.orZero(),
                imageUrl = imageUrl.orEmpty(),
                name = name.orEmpty(),
                rateAvg = rateAvg.orZero(),
                rateOwnerBeerId = rateOwner?.beerId.orZero(),
                rateOwnerRatio = rateOwner?.ratio.orZero(),
                rateOwnerUserId = rateOwner?.userId.orZero(),
                reviews = reviews.orEmpty(),
                thumbnailImage = thumbnailImage.orEmpty(),
                reviewOwnerBeer = reviewOwner?.beer,
                reviewOwnerContent = reviewOwner?.content.orEmpty(),
                reviewOwnerNickname = reviewOwner?.nickname.orEmpty(),
                reviewOwnerRatio = reviewOwner?.ratio.orDefault(0.5f),
                reviewOwnerUserId = reviewOwner?.userId.orZero(),
                initFavorite = favoriteFlag.orFalse()
            ).apply {
                this.eventNotifier = eventNotifier
            }
        }
    }

    private fun getNullBeerItem() : BeerItemViewModel =
        BeerItemViewModel(
            abv = 0f,
            aromas = emptyList(),
            beerStyle = "",
            brewery = "",
            country = "",
            id = 0,
            imageUrl = emptyList(),
            name = "",
            rateAvg = 0f,
            rateOwnerBeerId = 0,
            rateOwnerRatio = 0f,
            rateOwnerUserId = 0,
            reviews = emptyList(),
            thumbnailImage = "",
            reviewOwnerBeer = null,
            reviewOwnerContent = "",
            reviewOwnerNickname = "",
            reviewOwnerRatio = 0.5f,
            reviewOwnerUserId = 0,
            initFavorite = false
        )
}