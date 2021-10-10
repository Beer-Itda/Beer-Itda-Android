package com.ddd4.synesthesia.beer.presentation.ui.common.related.model

import com.ddd4.synesthesia.beer.presentation.base.entity.ItemClickEntity
import com.ddd4.synesthesia.beer.presentation.ui.common.related.RelatedType

sealed class RelatedSelectEntity : ItemClickEntity() {
    class SelectTitle(type: RelatedType) : RelatedSelectEntity()
}