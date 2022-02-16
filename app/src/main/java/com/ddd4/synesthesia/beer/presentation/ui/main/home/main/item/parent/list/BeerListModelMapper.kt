package com.ddd4.synesthesia.beer.presentation.ui.main.home.main.item.parent.list

import com.ddd4.synesthesia.beer.presentation.ui.common.beer.item.BeerItemViewModelMapper.getBeerItemViewModel
import com.ddd4.synesthesia.beer.presentation.ui.main.home.main.item.child.HomeBeerChildItemViewModel
import com.ddd4.synesthesia.beer.presentation.ui.main.home.main.view.HomeBeerRecommendType
import com.hjiee.core.event.SelectActionEventNotifier
import com.hjiee.domain.entity.DomainEntity.Beer

object BeerListModelMapper {

    fun List<Beer>?.getMapper(
        title: String,
        type: HomeBeerRecommendType,
        eventNotifier: SelectActionEventNotifier
    ): BeerListItemViewModel {
        val list = this?.map {
            HomeBeerChildItemViewModel(
                it.getBeerItemViewModel(eventNotifier)
            )
        }

        return BeerListItemViewModel(
            type = type,
            title = title,
            beers = list.orEmpty(),
            eventNotifier = eventNotifier
        )
    }


//    fun List<Beer>.getMapper(
//        sortType: SortType?,
//        type: HomeStringProvider.Code,
//        title: String,
//        style: List<StyleSmallItemViewModel>? = null,
//        aroma: List<AromaItemViewModel>? = null,
//        eventNotifier: SelectActionEventNotifier
//    ): BeerListItemViewModel {
//        val list = this.map {
//            HomeBeerChildItemViewModel(
//                it.getBeerItemViewModel(
//                    eventNotifier = eventNotifier
//                )
//            )
//
//        }
//        return BeerListItemViewModel(
//            sortType = sortType,
//            type = type,
//            title = title,
//            beers = list,
//            style = style.orEmpty(),
//            aroma = aroma.orEmpty(),
//            eventNotifier = eventNotifier
//        )
//    }

}