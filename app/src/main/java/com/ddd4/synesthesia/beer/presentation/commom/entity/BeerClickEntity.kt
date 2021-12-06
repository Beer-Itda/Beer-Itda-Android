package com.ddd4.synesthesia.beer.presentation.commom.entity

import com.hjiee.core.event.entity.ItemClickEntity
import com.ddd4.synesthesia.beer.presentation.ui.common.beer.item.BeerItemViewModel
import com.hjiee.domain.entity.DomainEntity

sealed class BeerClickEntity : ItemClickEntity() {
    class SelectItem(val beer: DomainEntity.Beer) : BeerClickEntity()
    class SelectBeer(val beer: BeerItemViewModel) : BeerClickEntity()
    class SelectFavorite(val beer: DomainEntity.Beer) : BeerClickEntity()
    class SelectFavorite2(val beer: BeerItemViewModel) : BeerClickEntity()
}