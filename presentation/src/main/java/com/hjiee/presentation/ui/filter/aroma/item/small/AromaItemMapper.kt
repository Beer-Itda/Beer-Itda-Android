package com.hjiee.presentation.ui.filter.aroma.item.small

import com.hjiee.core.event.SelectActionEventNotifier
import com.hjiee.core.ext.orFalse
import com.hjiee.domain.entity.DomainEntity
import com.hjiee.domain.entity.request.RequestSelectedAroma

object AromaItemMapper {

    fun List<DomainEntity.Aroma>?.getItem(
        eventNotifier: SelectActionEventNotifier
    ): List<AromaItemViewModel> {
        return getItems(this.orEmpty(), eventNotifier)
    }

    private fun getItems(
        items: List<DomainEntity.Aroma>,
        eventNotifier: SelectActionEventNotifier
    ): List<AromaItemViewModel> {
        return items.mapIndexed { index, item ->
            AromaItemViewModel(
                id = item.id,
                name = item.name,
                isAll = index == 0,
                position = index + 1,
                eventNotifier = eventNotifier
            ).apply {
                isSelected.set(item.isSelected)
            }
        }
    }

    /**
     * 선택된 향 데이터를 String으로 변환
     */
    fun getSelectedAromaString(
        allItems: List<AromaItemViewModel>,
        selectedItems: List<AromaItemViewModel>
    ): RequestSelectedAroma {
        return if (selectedItems.firstOrNull()?.id == -1) {
            // 전체 선택
            RequestSelectedAroma(
                allItems.filter { it.id != -1 }
                    .map { it.id }
                    .sorted()
                    .toString()
                    .removePrefix("[")
                    .removeSuffix("]")
            )
        } else {
            // 향 일부 선택
            RequestSelectedAroma(
                selectedItems.map { it.id }
                    .sorted()
                    .toString()
                    .removePrefix("[")
                    .removeSuffix("]")
            )
        }
    }
}