package com.ddd4.synesthesia.beer.presentation.commom.entity

import com.ddd4.synesthesia.beer.data.model.Related
import com.ddd4.synesthesia.beer.presentation.base.entity.ItemClickEntity

sealed class RelatedClickEntity : ItemClickEntity() {
    class SelectItem(val beer : Related) : RelatedClickEntity()
    class SelectFavorite(val beer : Related) : RelatedClickEntity()
}