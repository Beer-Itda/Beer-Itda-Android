package com.ddd4.synesthesia.beer.presentation.ui.detail.item.related

import com.ddd4.synesthesia.beer.presentation.ui.common.beer.item.BeerItemViewModelMapper.getBeerItemViewModel
import com.ddd4.synesthesia.beer.presentation.ui.common.related.RelatedItemViewModel
import com.ddd4.synesthesia.beer.presentation.ui.common.related.RelatedType
import com.hjiee.core.event.SelectEventNotifier
import com.hjiee.domain.entity.DomainEntity

object BeerDetailRelatedMapper {

    fun DomainEntity.BeerDetail.getRelatedListItemViewModel(
        type: RelatedType,
        eventNotifier: SelectEventNotifier
    ): BeerDetailRelatedListViewModel {
        return BeerDetailRelatedListViewModel(
            title = getTypeTitle(type),
            type = type,
            relatedBeerList = getTypeWithRelatedBeerList(type, eventNotifier),
            eventNotifier = eventNotifier,
        )
    }


//    fun RelatedBeers?.convertRelatedBeer(
//        type: RelatedType,
//        eventNotifier: SelectActionEventNotifier
//    ): List<RelatedItemViewModel> {
//        val relatedBeerList = mutableListOf<RelatedItemViewModel>()
//
//        this?.let {
//            when (type) {
//                RelatedType.AROMA -> {
//                    aromaRelated?.map {
//                        relatedBeerList.add(
//                            it.getRelatedBeer(
//                                beer = it.getBeerItemViewModel(eventNotifier),
//                                type = type,
//                                eventNotifier = eventNotifier
//                            )
//                        )
//                    }
//                }
//                RelatedType.STYLE -> {
//                    styleRelated?.map {
//                        relatedBeerList.add(
//                            it.getRelatedBeer(
//                                beer = it.getBeerItemViewModel(eventNotifier),
//                                type = type,
//                                eventNotifier = eventNotifier
//                            )
//                        )
//                    }
//                }
//                RelatedType.RANDOM -> {
//                    randomlyRelated?.map {
//                        relatedBeerList.add(
//                            it.getRelatedBeer(
//                                beer = it.getBeerItemViewModel(eventNotifier),
//                                type = type,
//                                eventNotifier = eventNotifier
//                            )
//                        )
//                    }
//                }
//            }
//        }
//        return relatedBeerList
//    }

    private fun List<DomainEntity.Beer>?.getRelatedBeer(
        type: RelatedType,
        eventNotifier: SelectEventNotifier
    ): List<RelatedItemViewModel> {
        return this?.map {
            RelatedItemViewModel(
                beer = it.getBeerItemViewModel(eventNotifier),
                type = type,
                eventNotifier = eventNotifier
            )
        }.orEmpty()
    }

    private fun DomainEntity.BeerDetail.getTypeWithRelatedBeerList(
        type: RelatedType,
        eventNotifier: SelectEventNotifier
    ): List<RelatedItemViewModel> {
        return when (type) {
            RelatedType.AROMA -> {
                relatedAromaBeer.getRelatedBeer(
                    type = type,
                    eventNotifier = eventNotifier
                )
            }
            RelatedType.STYLE -> {
                relatedStyleBeer.getRelatedBeer(
                    type = type,
                    eventNotifier = eventNotifier
                )
            }
            else -> {
                emptyList()
            }
//            RelatedType.RANDOM -> {
//                relatedRandom.orEmpty()
//            }
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