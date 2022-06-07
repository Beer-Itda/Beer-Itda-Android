package com.hjiee.presentation.ui.main.mypage.level.item

import com.hjiee.domain.entity.DomainEntity

object LevelMapper {
    fun List<DomainEntity.LevelGuide>.getItems(myLevelId: Int): List<LevelItemViewModel> {
        return map {
            LevelItemViewModel(
                id = it.id,
                level = it.level,
                levelImage = it.levelImage,
                levelCount = it.levelCount,
            ).apply {
                isMyLevel.set(myLevelId == it.id)
            }
        }
    }
}