package com.ddd4.synesthesia.beer.presentation.ui.filter.style.item.small

import com.ddd4.synesthesia.beer.presentation.ui.filter.style.item.large.StyleLargeItemViewModel
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
        val items = mutableListOf<StyleSmallItemViewModel>()
        items.add(
            getAllSelectCategory(
                smallId = -1,
                middleId = middleId,
                largePosition = largePosition,
                middlePosition = middlePosition,
                middleName = middleName,
                isAllSelected = all { it.isSelected },
                eventNotifier = eventNotifier
            )
        )
        items.addAll(
            getSmallCategory(
                middleId = middleId,
                largePosition = largePosition,
                middlePosition = middlePosition,
                middleName = middleName,
                eventNotifier = eventNotifier
            )
        )
        return items
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
//                id = "%03d".format(largePosition) + "%03d".format(middlePosition) + "%03d".format(
//                    smallPosition + 1
//                ),
                smallId = small.smallId,
                parentId = middleId,
                middleName = middleName,
                smallName = small.smallName,
                largePosition = largePosition,
                middlePosition = middlePosition,
                smallPosition = smallPosition + 1,
                isAll = false,
                eventNotifier = eventNotifier
            ).apply {
                isSelected.set(small.isSelected)
            }
        }
    }

    /**
     * 전체 선택하였을때
     */
    private fun getAllSelectCategory(
        smallId: Int,
        middleId: Int,
        largePosition: Int,
        middlePosition: Int,
        middleName: String,
        isAllSelected: Boolean,
        eventNotifier: SelectActionEventNotifier
    ): StyleSmallItemViewModel {
        return StyleSmallItemViewModel(
//            id = "%03d".format(largePosition) + "%03d".format(middlePosition) + "%03d".format(0),
            smallId = smallId,
            parentId = middleId,
            middleName = middleName,
            smallName = "전체선택",
            largePosition = largePosition,
            middlePosition = middlePosition,
            smallPosition = 0,
            isAll = true,
            eventNotifier = eventNotifier
        ).apply {
            isSelected.set(isAllSelected)
        }
    }


    /**
     * 선택된 스타일 데이터를 String으로 변환
     */
    fun getSelectedStyleString(
        allItems: List<StyleLargeItemViewModel>,
        selectedItems: List<StyleSmallItemViewModel>
    ): RequestSelectedStyle {
        return RequestSelectedStyle(
            selectedItems.map { small ->
                buildString {
                    if (small.smallId == -1) {
                        allItems.map { large ->
                            // 전체선택된 중분류 데이터
                            large.middleCategories.filter { middle ->
                                small.parentId == middle.middleId
                            }.map { filteredMiddle ->
                                // 전체 선택된 데이터 id 연결
                                // -1은 전체선택이기때문에 제외
                                filteredMiddle.smallCategories.filter {
                                    it.smallId != -1
                                }.map {
                                    append(it.smallId)
                                    append(",")
                                }.dropLast(1)
                            }
                        }
                    } else {
                        // 일부 선택된 데이터
                        append(small.smallId)
                    }
                }
            }.sorted()
                .toString()
                .replace(",,",",")
                .removePrefix("[")
                .removeSuffix("]")
        )
    }
}