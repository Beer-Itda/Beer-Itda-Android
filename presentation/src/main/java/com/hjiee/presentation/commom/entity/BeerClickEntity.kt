package com.hjiee.presentation.commom.entity

import com.hjiee.presentation.ui.common.beer.item.BeerItemViewModel
import com.hjiee.core.event.entity.ItemClickEntity
import com.hjiee.domain.entity.DomainEntity

sealed class BeerClickEntity : ItemClickEntity() {
    @Deprecated("")
    class ClickItem(val beer: DomainEntity.Beer) : BeerClickEntity()
    class ClickBeer(val beer: BeerItemViewModel) : BeerClickEntity()
    class ClickFavorite(val beer: BeerItemViewModel) : BeerClickEntity()
}