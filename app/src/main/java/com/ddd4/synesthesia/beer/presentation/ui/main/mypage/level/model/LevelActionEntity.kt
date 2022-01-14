package com.ddd4.synesthesia.beer.presentation.ui.main.mypage.level.model

import com.ddd4.synesthesia.beer.presentation.ui.main.mypage.level.item.LevelItemViewModel
import com.hjiee.core.event.entity.ActionEntity

sealed class LevelActionEntity : ActionEntity() {
    class UpdateUi(val items: List<LevelItemViewModel>) : LevelActionEntity()
}