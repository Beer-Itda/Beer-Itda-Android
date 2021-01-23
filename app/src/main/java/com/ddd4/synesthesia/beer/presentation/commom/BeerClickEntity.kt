package com.ddd4.synesthesia.beer.presentation.commom

import com.ddd4.synesthesia.beer.data.model.Beer
import com.ddd4.synesthesia.beer.presentation.base.entity.ItemClickEntity

sealed class BeerClickEntity : ItemClickEntity() {
    class SelectItem(val beer : Beer) : BeerClickEntity()
    class SelectFavorite(val beer : Beer) : BeerClickEntity()
}