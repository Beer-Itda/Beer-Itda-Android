package com.ddd4.synesthesia.beer.presentation.ui.main.mypage.level.item

import com.hjiee.domain.entity.DomainEntity

object LevelMapper {
    fun List<DomainEntity.LevelGuide>.getItem(): List<LevelItemViewModel> {
        return map {
            LevelItemViewModel(
                id = it.id,
                level = it.level,
                levelImage = "",
                levelCount = it.levelCount,
            )
        }
    }
}