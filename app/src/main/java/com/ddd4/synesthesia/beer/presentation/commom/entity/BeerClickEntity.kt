package com.ddd4.synesthesia.beer.presentation.commom.entity

import com.ddd4.synesthesia.beer.presentation.ui.common.beer.item.BeerItemViewModel
import com.hjiee.core.event.entity.ItemClickEntity
import com.hjiee.domain.entity.DomainEntity

sealed class BeerClickEntity : ItemClickEntity() {
    class ClickItem(val beer: DomainEntity.Beer) : BeerClickEntity()
    class ClickBeer(val beer: BeerItemViewModel) : BeerClickEntity()
    class ClickFavorite(val beer: BeerItemViewModel) : BeerClickEntity()
}