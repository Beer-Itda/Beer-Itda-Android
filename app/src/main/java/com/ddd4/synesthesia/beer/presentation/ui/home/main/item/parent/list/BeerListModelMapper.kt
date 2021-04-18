package com.ddd4.synesthesia.beer.presentation.ui.home.main.item.parent.list

import com.ddd4.synesthesia.beer.data.model.Beer
import com.ddd4.synesthesia.beer.presentation.base.event.SelectActionEventNotifier
import com.ddd4.synesthesia.beer.presentation.ui.common.beer.item.BeerItemViewModelMapper
import com.ddd4.synesthesia.beer.presentation.ui.common.filter.AromaProvider
import com.ddd4.synesthesia.beer.presentation.ui.common.filter.StyleProvider
import com.ddd4.synesthesia.beer.presentation.ui.filter.aroma.item.small.AromaItemViewModel
import com.ddd4.synesthesia.beer.presentation.ui.filter.style.item.small.StyleSmallItemViewModel
import com.ddd4.synesthesia.beer.presentation.ui.home.main.item.child.HomeBeerChildItemViewModel
import com.ddd4.synesthesia.beer.presentation.ui.home.main.view.HomeStringProvider
import com.ddd4.synesthesia.beer.util.sort.SortType

object BeerListModelMapper {

    fun List<Beer>.getMapper(
        sortType: SortType?,
        type: HomeStringProvider.Code,
        title: String,
        style: List<StyleSmallItemViewModel>? = null,
        aroma: List<AromaItemViewModel>? = null,
        eventNotifier: SelectActionEventNotifier
    ): BeerListItemViewModel {
        val list = this.map {
            HomeBeerChildItemViewModel(
                BeerItemViewModelMapper.getMapper(
                    beer = it,
                    eventNotifier = eventNotifier
                )
            )

        }
        return BeerListItemViewModel(
            sortType = sortType,
            type = type,
            title = title,
            beers = list,
            style = style.orEmpty(),
            aroma = aroma.orEmpty(),
            eventNotifier = eventNotifier
        )
    }

}