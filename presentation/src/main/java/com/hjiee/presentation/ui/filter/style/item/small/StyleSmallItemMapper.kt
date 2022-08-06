package com.hjiee.presentation.ui.filter.style.item.small

import com.hjiee.core.event.SelectActionEventNotifier
import com.hjiee.domain.entity.DomainEntity
import com.hjiee.domain.entity.request.RequestSelectedStyle

object StyleSmallItemMapper {

    fun List<DomainEntity.StyleSmallCategory>.getSmall(
        middleId: Int,
        middleName: String,
        largePosition: Int,
        middlePosition: Int,
        eventNotifier: SelectActionEventNotifier
    ): List<StyleSmallItemViewModel> {
        return getSmallCategory(
            middleId = middleId,
            largePosition = largePosition,
            middlePosition = middlePosition,
            middleName = middleName,
            eventNotifier = eventNotifier
        )
    }

    /**
     * 일부 스타일 선택하였을때
     */
    private fun List<DomainEntity.StyleSmallCategory>.getSmallCategory(
        middleId: Int,
        largePosition: Int,
        middlePosition: Int,
        middleName: String,
        eventNotifier: SelectActionEventNotifier
    ): List<StyleSmallItemViewModel> {
        return mapIndexed { smallPosition, small ->
            StyleSmallItemViewModel(
                smallId = small.smallId,
                parentId = middleId,
                middleName = middleName,
                smallName = small.smallName,
                largePosition = largePosition,
                middlePosition = middlePosition,
                smallPosition = smallPosition + 1,
                isAll = smallPosition == 0,
                eventNotifier = eventNotifier
            ).apply {
                isSelected.set(small.isSelected)
            }
        }
    }

    fun List<StyleSmallItemViewModel>.idList(): RequestSelectedStyle {
        return RequestSelectedStyle(
            styleIds = map { it.smallId }.toIntArray()
        )
    }
}