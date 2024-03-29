package com.hjiee.presentation.ui.common.beer.item

import com.hjiee.core.event.SelectEventNotifier
import com.hjiee.domain.entity.DomainEntity

object BeerItemViewModelMapper {

    fun DomainEntity.Beer?.getBeerItemViewModel(
        eventNotifier: SelectEventNotifier
    ): BeerItemViewModel {
        return if (this == null) {
            getNullBeerItem()
        } else {
            BeerItemViewModel(
                alcoholByVolume = abv,
                style = style,
                brewery = brewery,
                country = country,
                id = id,
                nameForKorean = nameForKorean,
                nameForEnglish = nameForEnglish,
                starAvg = starAvg,
                reviews = emptyList(),
                thumbnailImage = thumbnailImage,
                isFavorite = isFavorite,
                eventNotifier = eventNotifier
            )
        }
    }

    fun List<DomainEntity.Beer>?.getBeerItemViewModel(
        eventNotifier: SelectEventNotifier
    ): List<BeerItemViewModel> {
        return this?.map {
            BeerItemViewModel(
                alcoholByVolume = it.abv,
                style = it.style,
                brewery = it.brewery,
                country = it.country,
                id = it.id,
                nameForKorean = it.nameForKorean,
                nameForEnglish = it.nameForEnglish,
                starAvg = it.starAvg,
                reviews = emptyList(),
                thumbnailImage = it.thumbnailImage,
                isFavorite = it.isFavorite,
                eventNotifier = eventNotifier
            )
        }.orEmpty()
    }

    private fun getNullBeerItem(): BeerItemViewModel =
        BeerItemViewModel(
            alcoholByVolume = 0f,
            aromas = emptyList(),
            style = "",
            brewery = "",
            country = "",
            id = 0,
            imageUrl = emptyList(),
            nameForKorean = "",
            nameForEnglish = "",
            starAvg = 0f,
            reviews = emptyList(),
            thumbnailImage = "",
            isFavorite = false,
            eventNotifier = null
        )
}