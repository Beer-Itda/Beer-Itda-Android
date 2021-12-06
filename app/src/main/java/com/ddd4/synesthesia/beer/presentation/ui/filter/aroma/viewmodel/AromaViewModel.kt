package com.ddd4.synesthesia.beer.presentation.ui.filter.aroma.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.ddd4.synesthesia.beer.presentation.base.BaseViewModel
import com.hjiee.core.event.entity.ItemClickEntity
import com.ddd4.synesthesia.beer.presentation.ui.common.filter.AromaProvider
import com.ddd4.synesthesia.beer.presentation.ui.common.filter.FliterStringProvider
import com.ddd4.synesthesia.beer.presentation.ui.filter.aroma.entity.AromaActionEntity
import com.ddd4.synesthesia.beer.presentation.ui.filter.aroma.entity.AromaClickEntity
import com.ddd4.synesthesia.beer.presentation.ui.filter.aroma.item.small.AromaItemMapper
import com.ddd4.synesthesia.beer.presentation.ui.filter.aroma.item.small.AromaItemViewModel
import com.ddd4.synesthesia.beer.presentation.ui.filter.aroma.view.AromaViewState
import com.hjiee.domain.NetworkCallback
import com.hjiee.domain.NetworkResponse
import com.hjiee.domain.usecase.filter.aroma.GetAromaUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

class AromaViewModel @ViewModelInject constructor(
    private val aromaUseCase: GetAromaUseCase,
    private val stringProvider: FliterStringProvider,
    private val aromaProvider: AromaProvider
) : BaseViewModel() {

    companion object {
        const val MAX_AROMA_COUNT = 5
    }

    val viewState = AromaViewState()
    private val selectedList = mutableListOf<AromaItemViewModel>()

    private lateinit var items: List<AromaItemViewModel>

    fun load() {
        viewModelScope.launch(errorHandler) {
            aromaUseCase.execute { response ->
                when (response) {
                    is NetworkResponse.Success -> {
                        items = AromaItemMapper.get(
                            items = response.data,
                            eventNotifier = this@AromaViewModel
                        )
                        initSelectedAroma()
                        notifyActionEvent(AromaActionEntity.UpdateList(items))
                    }
                    is NetworkResponse.NoContents -> {
                    }
                    is NetworkResponse.Error -> {
                    }
                }
            }
        }
    }

    override fun handleSelectEvent(entity: ItemClickEntity) {
        when (entity) {
            is AromaClickEntity.AddAroma -> {
                when {
                    viewState.isMaxSelected.get().not() &&
                            isContainsSelectedItem(entity.item).not() -> {
                        addSelectedItem(entity.item)
                    }

                    isContainsSelectedItem(entity.item) -> {
                        removeSelectedItem(entity.item)
                    }

                    viewState.isMaxSelected.get() -> {
                        oneOfTheFullSmallSelections(entity.item)
                    }

                    else -> {

                    }
                }
            }
            is AromaClickEntity.DeleteAroma -> {
                selectedList.remove(entity.item)
                setSelectedStatusChange(entity.item, false)
                notifyActionEvent(AromaActionEntity.UpdateSelectedList(selectedList))
                setMaxSelectedCount()
            }
        }
    }

    /**
     * 선택된 아이템 추가
     */
    private fun addSelectedItem(item: AromaItemViewModel) {
        resetCurrentSelectedItem()
        selectedList.add(0, item)
        setSelectedStatusChange(item, true)
        notifyActionEvent(AromaActionEntity.UpdateSelectedList(selectedList))
        setMaxSelectedCount()
    }

    /**
     * 선택된 아이템 제거와 아이템 선택상태 변경
     */
    private fun resetCurrentSelectedItem() {
        if (items[0].isSelected.get()) {
            selectedList.removeAll(items)
            items.forEachIndexed { index, aroma ->
                aroma.isSelected.set(false)
            }
        }
    }

    /**
     * 선택된 아이템이 max값일때 현재 아이템에 전체선택 or 아이템선택 여부에 따라 상태 변경
     */
    private fun oneOfTheFullSmallSelections(item: AromaItemViewModel) {
        if (items[0].isSelected.get()) {
            addSelectedItem(item)
        } else if (item.isAll && isContainsSelectedItem(item).not()) {
            addSelectedItem(item)
        } else {
            notifyActionEvent(
                AromaActionEntity.ShowToast(
                    stringProvider.getStringRes(
                        FliterStringProvider.Code.MAX_AROMA
                    )
                )
            )
        }
    }

    /**
     * 선택된 아이템 삭제
     */
    private fun removeSelectedItem(item: AromaItemViewModel) {
        selectedList.remove(item)
        setSelectedStatusChange(item, false)
        notifyActionEvent(AromaActionEntity.UpdateSelectedList(selectedList))
        setMaxSelectedCount()
    }

    /**
     * 아이템 선택상태 변경
     */
    private fun setSelectedStatusChange(item: AromaItemViewModel, isSelected: Boolean) {
        if (item.isAll) {
            items.forEachIndexed { index, aroam ->
                if (index != 0) {
                    removeSelectedItem(aroam)
                }
                aroam.isSelected.set(isSelected)
            }
        } else {
            item.isSelected.set(isSelected)
        }
    }

    /**
     * 클릭한 아이템이 선택된 리스트에 포함되는지
     */
    private fun isContainsSelectedItem(
        item: AromaItemViewModel
    ): Boolean {
        return selectedList.find {
            it == item
        }.let {
            it != null
        }

    }

    @ExperimentalCoroutinesApi
    fun clickDone() {
        aromaProvider.data = selectedList.map {
            it.eventNotifier = null
            it
        }
        notifyActionEvent(AromaActionEntity.SelectDone(selectedList))
    }

    private fun initSelectedAroma() {
        selectedList.clear()
        aromaProvider.data?.map { saved ->
            items[saved.position].let { item ->
                setSelectedStatusChange(item, true)
                selectedList.add(item)
            }
        }
        setMaxSelectedCount()
        notifyActionEvent(AromaActionEntity.UpdateSelectedList(selectedList))
    }

    private fun setMaxSelectedCount() {
        viewState.setIsMaxSelected(
            isEmpty = selectedList.isEmpty(),
            size = if (items[0].isSelected.get()) MAX_AROMA_COUNT else selectedList.size,
            maxCount = MAX_AROMA_COUNT,
        )
    }
}