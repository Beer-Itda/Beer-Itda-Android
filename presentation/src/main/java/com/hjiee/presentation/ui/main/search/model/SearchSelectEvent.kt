package com.hjiee.presentation.ui.main.search.model

import com.hjiee.core.event.entity.ItemClickEntity
import com.hjiee.domain.entity.DomainEntity

sealed class SearchSelectEvent : ItemClickEntity() {
    class SelectItem(val beer: DomainEntity.Beer) : SearchSelectEvent()
    object Inquire : SearchSelectEvent()
}