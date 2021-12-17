package com.ddd4.synesthesia.beer.presentation.ui.main.search.model

import com.hjiee.core.event.entity.ItemClickEntity

sealed class SearchSelectEvent : ItemClickEntity() {
    object Inquire : SearchSelectEvent()
}