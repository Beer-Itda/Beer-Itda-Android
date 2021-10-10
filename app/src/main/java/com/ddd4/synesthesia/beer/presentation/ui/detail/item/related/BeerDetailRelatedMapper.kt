package com.ddd4.synesthesia.beer.presentation.ui.detail.item.related

import com.ddd4.synesthesia.beer.data.model.Related
import com.ddd4.synesthesia.beer.data.model.RelatedBeers
import com.ddd4.synesthesia.beer.ext.orZero
import com.ddd4.synesthesia.beer.presentation.base.event.SelectActionEventNotifier
import com.ddd4.synesthesia.beer.presentation.base.event.SelectEventNotifier
import com.ddd4.synesthesia.beer.presentation.ui.common.beer.item.BeerItemViewModel
import com.ddd4.synesthesia.beer.presentation.ui.common.beer.item.BeerItemViewModelMapper.getBeerItemViewModel
import com.ddd4.synesthesia.beer.presentation.ui.common.related.RelatedItemViewModel
import com.ddd4.synesthesia.beer.presentation.ui.common.related.RelatedType
import com.ddd4.synesthesia.beer.presentation.ui.detail.item.BeerDetailItemViewModel

object BeerDetailRelatedMapper {

    fun BeerDetailItemViewModel.getRelatedListItemViewModel(
        eventNotifier: SelectEventNotifier,
        type: RelatedType
    ): BeerDetailRelatedListViewModel {
        return BeerDetailRelatedListViewModel(
            title = getTypeTitle(type),
            type = type,
            relatedBeerList = getTypeWithRelatedBeerList(type),
            eventNotifier = eventNotifier,
        )
    }


    fun RelatedBeers?.convertRelatedBeer(
        type: RelatedType,
        eventNotifier: SelectActionEventNotifier
    ): List<RelatedItemViewModel> {
        val relatedBeerList = mutableListOf<RelatedItemViewModel>()

        this?.let {
            when (type) {
                RelatedType.AROMA -> {
                    aromaRelated?.map {
                        relatedBeerList.add(
                            it.getRelatedBeer(
                                beer = it.getBeerItemViewModel(eventNotifier),
                                type = type,
                                eventNotifier = eventNotifier
                            )
                        )
                    }
                }
                RelatedType.STYLE -> {
                    styleRelated?.map {
                        relatedBeerList.add(
                            it.getRelatedBeer(
                                beer = it.getBeerItemViewModel(eventNotifier),
                                type = type,
                                eventNotifier = eventNotifier
                            )
                        )
                    }
                }
                RelatedType.RANDOM -> {
                    randomlyRelated?.map {
                        relatedBeerList.add(
                            it.getRelatedBeer(
                                beer = it.getBeerItemViewModel(eventNotifier),
                                type = type,
                                eventNotifier = eventNotifier
                            )
                        )
                    }
                }
            }
        }
        return relatedBeerList
    }

    private fun Related.getRelatedBeer(
        beer: BeerItemViewModel,
        type: RelatedType,
        eventNotifier: SelectActionEventNotifier
    ): RelatedItemViewModel {
        return RelatedItemViewModel(
            beer = beer,
            id = id.orZero(),
            abv = abv.orZero(),
            aroma = aroma.orEmpty(),
            beerStyle = beerStyle.orEmpty(),
            brewery = brewery.orEmpty(),
            country = country.orEmpty(),
            name = name.orEmpty(),
            rateAvg = rateAvg.orZero(),
            thumbnailImage = thumbnailImage.orEmpty(),
            type = type,
            eventNotifier = eventNotifier
        )
    }

    private fun BeerDetailItemViewModel.getTypeWithRelatedBeerList(
        type: RelatedType
    ): List<RelatedItemViewModel> {
        return when (type) {
            RelatedType.AROMA -> {
                relatedAroma.orEmpty()
            }
            RelatedType.STYLE -> {
                relatedStyle.orEmpty()
            }
            RelatedType.RANDOM -> {
                relatedRandom.orEmpty()
            }
        }
    }

    private fun getTypeTitle(type: RelatedType): String {
        return when (type) {
            RelatedType.AROMA -> {
                "비슷한향이 나는 맥주입니다 🍒"
            }
            RelatedType.STYLE -> {
                "같은 스타일의 맥주입니다 🍹"
            }
            RelatedType.RANDOM -> {
                "추천하는 맥주입니다 🏆"
            }
        }
    }
}