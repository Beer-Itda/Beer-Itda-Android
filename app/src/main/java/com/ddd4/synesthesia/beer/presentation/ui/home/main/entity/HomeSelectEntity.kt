package com.ddd4.synesthesia.beer.presentation.ui.home.main.entity

import com.ddd4.synesthesia.beer.presentation.base.entity.ItemClickEntity
import com.ddd4.synesthesia.beer.presentation.ui.common.filter.BeerFilter
import com.ddd4.synesthesia.beer.presentation.ui.home.main.view.HomeStringProvider
import com.ddd4.synesthesia.beer.util.sort.SortType

sealed class HomeSelectEntity : ItemClickEntity() {
    object ClickFilter : HomeSelectEntity()
    object Search : HomeSelectEntity()
    object MyPage : HomeSelectEntity()
    object Sort : HomeSelectEntity()
    class ClickTitle(
        val sort: SortType?,
        val type: HomeStringProvider.Code,
        val title: String,
        val filter: BeerFilter
    ) :
        HomeSelectEntity()
}