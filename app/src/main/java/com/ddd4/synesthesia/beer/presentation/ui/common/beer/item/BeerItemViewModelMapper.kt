package com.ddd4.synesthesia.beer.presentation.ui.common.beer.item

import com.hjiee.core.event.SelectActionEventNotifier
import com.hjiee.core.ext.orZero
import com.hjiee.domain.entity.DomainEntity

object BeerItemViewModelMapper {

    fun DomainEntity.Beer?.getBeerItemViewModel(eventNotifier: SelectActionEventNotifier): BeerItemViewModel {
        return if (this == null) {
            getNullBeerItem(eventNotifier)
        } else {
            BeerItemViewModel(
                alcoholByVolume = abv.orZero(),
//                aromas = aromas.orEmpty(),
//                beerStyle = beerStyle.orEmpty(),
//                brewery = brewery.orEmpty(),
//                country = country.orEmpty(),
                id = id,
//                imageUrl = imageUrl.orEmpty(),
                name = krName,
                starAvg = starAvg.orZero(),
//                reviews = reviews.orEmpty(),
                thumbnailImage = thumbnailImage,
//                initFavorite = favoriteFlag.orFalse(),
                eventNotifier = eventNotifier
            )
        }
    }

//    fun Related?.getBeerItemViewModel(eventNotifier: SelectActionEventNotifier): BeerItemViewModel {
//        return if (this == null) {
//            getNullBeerItem(eventNotifier)
//        } else {
//            BeerItemViewModel(
//                alcoholByVolume = abv.orZero(),
//                aromas = aroma.orEmpty(),
//                beerStyle = beerStyle.orEmpty(),
//                brewery = brewery.orEmpty(),
//                country = country.orEmpty(),
//                id = id.orZero(),
//                name = name.orEmpty(),
//                rateAvg = rateAvg.orZero(),
//                thumbnailImage = thumbnailImage.orEmpty(),
//                initFavorite = false,
//                eventNotifier = eventNotifier
//            )
//        }
//    }

    private fun getNullBeerItem(eventNotifier: SelectActionEventNotifier): BeerItemViewModel =
        BeerItemViewModel(
            alcoholByVolume = 0f,
            aromas = emptyList(),
//            beerStyle = "",
//            brewery = "",
//            country = "",
            id = 0,
            imageUrl = emptyList(),
            name = "",
            starAvg = 0f,
            reviews = emptyList(),
            thumbnailImage = "",
//            initFavorite = false,
            eventNotifier = eventNotifier
        )
}