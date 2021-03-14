package com.ddd4.synesthesia.beer.presentation.commom.entity

import com.ddd4.synesthesia.beer.data.model.Beer
import com.ddd4.synesthesia.beer.presentation.base.entity.ItemClickEntity
import com.ddd4.synesthesia.beer.presentation.ui.common.beer.item.BeerItemViewModel

sealed class BeerClickEntity : ItemClickEntity() {
    class SelectItem(val beer: Beer) : BeerClickEntity()
    class SelectBeer(val beer: BeerItemViewModel) : BeerClickEntity()
    class SelectFavorite(val beer: Beer) : BeerClickEntity()
    class SelectFavorite2(val beer: BeerItemViewModel) : BeerClickEntity()
}