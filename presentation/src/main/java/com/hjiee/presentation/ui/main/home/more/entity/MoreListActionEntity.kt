package com.hjiee.presentation.ui.main.home.more.entity

import com.hjiee.presentation.ui.main.home.more.item.IMoreListViewModel
import com.hjiee.core.event.entity.ActionEntity

sealed class MoreListActionEntity : ActionEntity() {
    object Refresh : MoreListActionEntity()
    class UpdateList(val data: List<IMoreListViewModel>?) : MoreListActionEntity()
    class MoveToDetail(val id: Int) : MoreListActionEntity()

}