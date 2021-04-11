package com.ddd4.synesthesia.beer.presentation.ui.home.like.entity

import com.ddd4.synesthesia.beer.presentation.base.entity.ActionEntity
import com.ddd4.synesthesia.beer.presentation.ui.home.like.item.IHomeLikeViewModel

sealed class HomeLikeActionEntity : ActionEntity() {
    object Refresh : HomeLikeActionEntity()
    class UpdateList(val data: List<IHomeLikeViewModel>?) : HomeLikeActionEntity()
    class MoveToDetail(val id: Int) : HomeLikeActionEntity()

}