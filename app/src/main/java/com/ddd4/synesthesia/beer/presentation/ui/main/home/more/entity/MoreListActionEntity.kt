package com.ddd4.synesthesia.beer.presentation.ui.main.home.more.entity

import com.hjiee.core.event.entity.ActionEntity
import com.ddd4.synesthesia.beer.presentation.ui.main.home.more.item.IMoreListViewModel

sealed class MoreListActionEntity : ActionEntity() {
    object Refresh : MoreListActionEntity()
    class UpdateList(val data: List<IMoreListViewModel>?) : MoreListActionEntity()
    class MoveToDetail(val id: Int) : MoreListActionEntity()

}