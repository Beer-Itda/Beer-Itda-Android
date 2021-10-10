package com.ddd4.synesthesia.beer.presentation.ui.common.beer.item

import com.ddd4.synesthesia.beer.data.model.Beer
import com.ddd4.synesthesia.beer.data.model.Related
import com.ddd4.synesthesia.beer.ext.orFalse
import com.ddd4.synesthesia.beer.ext.orZero
import com.ddd4.synesthesia.beer.presentation.base.event.SelectActionEventNotifier

object BeerItemViewModelMapper {

    fun Beer?.getBeerItemViewModel(eventNotifier: SelectActionEventNotifier): BeerItemViewModel {
        return if (this == null) {
            getNullBeerItem(eventNotifier)
        } else {
            BeerItemViewModel(
                alcoholByVolume = abv.orZero(),
                aromas = aromas.orEmpty(),
                beerStyle = beerStyle.orEmpty(),
                brewery = brewery.orEmpty(),
                country = country.orEmpty(),
                id = id.orZero(),
                imageUrl = imageUrl.orEmpty(),
                name = name.orEmpty(),
                rateAvg = rateAvg.orZero(),
                reviews = reviews.orEmpty(),
                thumbnailImage = thumbnailImage.orEmpty(),
                initFavorite = favoriteFlag.orFalse(),
                eventNotifier = eventNotifier
            )
        }
    }

    fun Related?.getBeerItemViewModel(eventNotifier: SelectActionEventNotifier): BeerItemViewModel {
        return if (this == null) {
            getNullBeerItem(eventNotifier)
        } else {
            BeerItemViewModel(
                alcoholByVolume = abv.orZero(),
                aromas = aroma.orEmpty(),
                beerStyle = beerStyle.orEmpty(),
                brewery = brewery.orEmpty(),
                country = country.orEmpty(),
                id = id.orZero(),
                name = name.orEmpty(),
                rateAvg = rateAvg.orZero(),
                thumbnailImage = thumbnailImage.orEmpty(),
                initFavorite = false,
                eventNotifier = eventNotifier
            )
        }
    }

    private fun getNullBeerItem(eventNotifier: SelectActionEventNotifier): BeerItemViewModel =
        BeerItemViewModel(
            alcoholByVolume = 0f,
            aromas = emptyList(),
            beerStyle = "",
            brewery = "",
            country = "",
            id = 0,
            imageUrl = emptyList(),
            name = "",
            rateAvg = 0f,
            reviews = emptyList(),
            thumbnailImage = "",
            initFavorite = false,
            eventNotifier = eventNotifier
        )
}