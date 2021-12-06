package com.ddd4.synesthesia.beer.presentation.commom.entity

import com.hjiee.core.event.entity.ActionEntity
import com.hjiee.core.event.entity.ItemClickEntity

interface HandleEvent {
    fun handleSelectEvent(entity: ItemClickEntity)
    fun handleActionEvent(entity: ActionEntity)
}