package com.ddd4.synesthesia.beer.presentation.ui.filter.aroma.viewmodel

import androidx.lifecycle.viewModelScope
import com.ddd4.synesthesia.beer.presentation.base.BaseViewModel
import com.ddd4.synesthesia.beer.presentation.ui.common.filter.FilterStringProvider
import com.ddd4.synesthesia.beer.presentation.ui.filter.aroma.entity.AromaActionEntity
import com.ddd4.synesthesia.beer.presentation.ui.filter.aroma.entity.AromaClickEntity
import com.ddd4.synesthesia.beer.presentation.ui.filter.aroma.item.small.AromaItemMapper
import com.ddd4.synesthesia.beer.presentation.ui.filter.aroma.item.small.AromaItemMapper.getItem
import com.ddd4.synesthesia.beer.presentation.ui.filter.aroma.item.small.AromaItemViewModel
import com.hjiee.core.event.entity.ItemClickEntity
import com.hjiee.core.ext.orFalse
import com.hjiee.core.manager.Change
import com.hjiee.core.manager.DataChangeManager
import com.hjiee.core.util.log.L
import com.hjiee.domain.usecase.filter.aroma.GetAromaUseCase
import com.hjiee.domain.usecase.filter.aroma.PostAromaUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AromaViewModel @Inject constructor(
    private val aromaUseCase: GetAromaUseCase,
    private val selectUseCase: PostAromaUseCase,
    private val stringProvider: FilterStringProvider
) : BaseViewModel() {

    companion object {
        const val MAX_AROMA_COUNT = 5
    }

    val viewState = AromaViewState()
    private val selectedList = mutableListOf<AromaItemViewModel>()
    private val selectedIdList
        get() = AromaItemMapper.getSelectedAromaString(items, selectedList)

    private val items = mutableListOf<AromaItemViewModel>()

    fun load() {
        statusLoading()
        viewModelScope.launch {
            runCatching {
                aromaUseCase.execute().getItem(eventNotifier = this@AromaViewModel)
            }.onSuccess {
                items.clear()
                items.addAll(it)
                initSelectedAroma()
                notifyActionEvent(AromaActionEntity.UpdateList(items))
                statusSuccess()
            }.onFailure {
                statusFailure()
                L.e(it)
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
     * ????????? ????????? ??????
     */
    private fun addSelectedItem(item: AromaItemViewModel) {
        resetCurrentSelectedItem()
        selectedList.add(0, item)
        setSelectedStatusChange(item, true)
        notifyActionEvent(AromaActionEntity.UpdateSelectedList(selectedList))
        setMaxSelectedCount()
    }

    /**
     * ????????? ????????? ????????? ????????? ???????????? ??????
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
     * ????????? ???????????? max????????? ?????? ???????????? ???????????? or ??????????????? ????????? ?????? ?????? ??????
     */
    private fun oneOfTheFullSmallSelections(item: AromaItemViewModel) {
        if (items.firstOrNull()?.isSelected?.get().orFalse()) {
            addSelectedItem(item)
        } else if (item.isAll && isContainsSelectedItem(item).not()) {
            addSelectedItem(item)
        } else {
            notifyActionEvent(
                AromaActionEntity.ShowToast(
                    stringProvider.getString(
                        FilterStringProvider.Code.MAX_AROMA
                    )
                )
            )
        }
    }

    /**
     * ????????? ????????? ??????
     */
    private fun removeSelectedItem(item: AromaItemViewModel) {
        selectedList.remove(item)
        setSelectedStatusChange(item, false)
        notifyActionEvent(AromaActionEntity.UpdateSelectedList(selectedList))
        setMaxSelectedCount()
    }

    /**
     * ????????? ???????????? ??????
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
     * ????????? ???????????? ????????? ???????????? ???????????????
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

    fun clickDone() {
        viewModelScope.launch {
            runCatching {
                selectUseCase.execute(selectedIdList)
            }.onSuccess {
                DataChangeManager.changed(Change.AROMA)
                notifySelectEvent(AromaClickEntity.SelectDone)
            }.onFailure {
                throwMessage(stringProvider.getErrorMessage())
                L.e(it)
            }
        }

    }

    fun clickSkip() {
        notifySelectEvent(AromaClickEntity.Skip)
    }

    private fun initSelectedAroma() {
        selectedList.clear()
        if (items.all { it.isSelected.get() }) {
            selectedList.add(items.first())
        } else {
            items.filter { it.isSelected.get() }.map {
                selectedList.add(it)
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