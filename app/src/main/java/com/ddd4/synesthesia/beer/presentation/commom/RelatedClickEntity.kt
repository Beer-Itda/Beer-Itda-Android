package com.ddd4.synesthesia.beer.presentation.commom

import com.ddd4.synesthesia.beer.data.model.Related
import com.ddd4.synesthesia.beer.presentation.base.entity.ItemClickEntity

sealed class RelatedClickEntity : ItemClickEntity() {
    class SelectItem(val beer : Related) : RelatedClickEntity()
    class SelectFavorite(val beer : Related) : RelatedClickEntity()
}