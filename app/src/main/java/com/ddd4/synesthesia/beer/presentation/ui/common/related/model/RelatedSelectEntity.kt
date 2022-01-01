package com.ddd4.synesthesia.beer.presentation.ui.common.related.model

import com.ddd4.synesthesia.beer.presentation.ui.common.related.RelatedType
import com.hjiee.core.event.entity.ItemClickEntity

sealed class RelatedSelectEntity : ItemClickEntity() {
    class SelectTitle(type: RelatedType) : RelatedSelectEntity()
}