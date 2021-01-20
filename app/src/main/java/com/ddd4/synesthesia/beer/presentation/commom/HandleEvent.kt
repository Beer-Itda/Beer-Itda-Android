package com.ddd4.synesthesia.beer.presentation.commom

import com.ddd4.synesthesia.beer.presentation.base.entity.ActionEntity
import com.ddd4.synesthesia.beer.presentation.base.entity.ItemClickEntity

interface HandleEvent {
    fun handleSelectEvent(entity : ItemClickEntity)
    fun handleActionEvent(entity : ActionEntity)
}