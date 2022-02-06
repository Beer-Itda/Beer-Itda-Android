package com.ddd4.synesthesia.beer.presentation.ui.filter.style.viewmodel

import androidx.lifecycle.viewModelScope
import com.ddd4.synesthesia.beer.presentation.base.BaseViewModel
import com.ddd4.synesthesia.beer.presentation.ui.common.filter.FilterStringProvider
import com.ddd4.synesthesia.beer.presentation.ui.filter.aroma.entity.AromaClickEntity
import com.ddd4.synesthesia.beer.presentation.ui.filter.style.entity.StyleActionEntity
import com.ddd4.synesthesia.beer.presentation.ui.filter.style.entity.StyleClickEntity
import com.ddd4.synesthesia.beer.presentation.ui.filter.style.item.large.StyleLargeItemMapper.getLarge
import com.ddd4.synesthesia.beer.presentation.ui.filter.style.item.large.StyleLargeItemViewModel
import com.ddd4.synesthesia.beer.presentation.ui.filter.style.item.middle.StyleMiddleItemViewModel
import com.ddd4.synesthesia.beer.presentation.ui.filter.style.item.small.StyleSmallItemMapper
import com.ddd4.synesthesia.beer.presentation.ui.filter.style.item.small.StyleSmallItemViewModel
import com.ddd4.synesthesia.beer.presentation.ui.filter.style.view.StyleViewState
import com.hjiee.core.event.entity.ItemClickEntity
import com.hjiee.core.manager.Change
import com.hjiee.core.manager.DataChangeManager
import com.hjiee.core.util.log.L
import com.hjiee.domain.usecase.filter.style.GetStyleUseCase
import com.hjiee.domain.usecase.filter.style.PostStyleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StyleViewModel @Inject constructor(
    private val styleUseCase: GetStyleUseCase,
    private val selectUseCase: PostStyleUseCase,
    private val stringProvider: FilterStringProvider
) : BaseViewModel() {

    companion object {
        const val MAX_STYLE_COUNT = 5
    }

    val viewState = StyleViewState()

    private val allCategories = mutableListOf<StyleLargeItemViewModel>()
    private val selectedCategory = mutableListOf<StyleSmallItemViewModel>()
    private val selectedCategoryIds get() = StyleSmallItemMapper.getSelectedStyleString(allCategories, selectedCategory)
    private val currentMiddleCategory = mutableListOf<StyleMiddleItemViewModel>()
    private val currentSmallCategory = mutableListOf<StyleSmallItemViewModel>()

    fun load() {
        statusLoading()
        viewModelScope.launch(errorHandler) {
            runCatching {
                styleUseCase.execute()
            }.onSuccess {
                val large = it.getLarge(eventNotifier = this@StyleViewModel)
                val middle = large.firstOrNull()?.middleCategories.orEmpty()
                val small = middle.firstOrNull()?.smallCategories.orEmpty()

                allCategories.addAll(large)
                currentMiddleCategory.addAll(middle)
                initSelectedStyle()
                notifyActionEvent(StyleActionEntity.UpdateLarge(large))
                notifyActionEvent(StyleActionEntity.UpdateMiddle(middle))
                notifyActionEvent(StyleActionEntity.UpdateSmall(small))
                statusSuccess()
            }.onFailure {
                statusFailure()
                L.e(it)
            }
        }
    }

    fun selectLargeCategory(position: Int) {
        currentMiddleCategory.clear()
        currentMiddleCategory.addAll(allCategories[position].middleCategories)
        notifyActionEvent(StyleActionEntity.UpdateMiddle(currentMiddleCategory))
    }

    fun selectMiddleCategory(position: Int) {
        currentMiddleCategory.filterIndexed { index, item ->
            item.isSelected.set(false)
            index == position
        }.map { item ->
            item.isSelected.set(true)
        }
        viewState.description.set(currentMiddleCategory[position].description)
        currentSmallCategory.clear()
        currentSmallCategory.addAll(currentMiddleCategory[position].smallCategories)
        notifyActionEvent(StyleActionEntity.UpdateSmall(currentSmallCategory))

    }

    override fun handleSelectEvent(entity: ItemClickEntity) {
        when (entity) {
            is StyleClickEntity.AddStyle -> {
                when {
                    //
                    viewState.isMaxSelected.get().not() &&
                            isContainsSelectedItem(entity.style).not() -> {
                        addSelectedStyle(entity.style)
                    }

                    //
                    isContainsSelectedItem(entity.style) -> {
                        removeSelectedStyle(entity.style)
                    }

                    //
                    viewState.isMaxSelected.get() -> {
                        oneOfTheFullSmallSelections(entity.style)
                    }
                    else -> {

                    }
                }
            }

            is StyleClickEntity.DeleteStyle -> {
                removeSelectedStyle(entity.style)
            }
        }
    }

    /**
     * 선택된 스타일 추가
     */
    private fun addSelectedStyle(item: StyleSmallItemViewModel) {
        resetCurrentSelectedStyle()
        selectedCategory.add(0, item)
        setSelectedStatusChange(item, true)
        notifyActionEvent(StyleActionEntity.UpdateSelectedStyleList(selectedCategory))
        setMaxSelectedCount()
    }

    /**
     * 선택된 스타일 삭제
     */
    private fun removeSelectedStyle(item: StyleSmallItemViewModel) {
        selectedCategory.remove(item)
        setSelectedStatusChange(item, false)
        notifyActionEvent(StyleActionEntity.UpdateSelectedStyleList(selectedCategory))
        setMaxSelectedCount()
    }

    /**
     * 선택된 스타일 모두 삭제
     */
    private fun removeAllCurrentStyleList() {
        selectedCategory.removeAll(currentSmallCategory)
        notifyActionEvent(StyleActionEntity.UpdateSelectedStyleList(selectedCategory))
    }

    /**
     * 선택된 스타일 제거와 소분류에 선택상태 변경
     */
    private fun resetCurrentSelectedStyle() {
        if (currentSmallCategory[0].isSelected.get()) {
            removeAllCurrentStyleList()
            currentSmallCategory.forEachIndexed { index, style ->
                style.isSelected.set(false)
            }
        }
    }

    /**
     * 선택된 스타일이 max값일때 현재 소분류 스타일에 전체선택 or 아이템선택 여부에 따라 상태 변경
     */
    private fun oneOfTheFullSmallSelections(item: StyleSmallItemViewModel) {
        if (currentSmallCategory[0].isSelected.get()) {
            addSelectedStyle(item)
        } else if (item.isAll && isContainsSelectedItem(item).not()) {
            addSelectedStyle(item)
        } else {
            notifyActionEvent(
                StyleActionEntity.ShowToast(
                    stringProvider.getString(
                        FilterStringProvider.Code.MAX_STYLE
                    )
                )
            )
        }
    }

    /**
     * 스타일 선택상태 변경
     */
    private fun setSelectedStatusChange(item: StyleSmallItemViewModel, isSelected: Boolean) {
        if (item.isAll) {
            allCategories[item.largePosition]
                .middleCategories[item.middlePosition]
                .smallCategories
                .forEachIndexed { index, small ->
                    if (index != 0) {
                        removeSelectedStyle(small)
                    }
                    small.isSelected.set(isSelected)
                }
        } else {
            item.isSelected.set(isSelected)
        }
    }

    /**
     * 클릭한 아이템이 선택된 리스트에 포함되는지
     */
    private fun isContainsSelectedItem(
        item: StyleSmallItemViewModel
    ): Boolean {
        return selectedCategory.find {
            it == item
        }.let {
            it != null
        }

    }

    fun clickDone() {
        viewModelScope.launch {
            runCatching {
                selectUseCase.execute(selectedCategoryIds)
            }.onSuccess {
                DataChangeManager.changed(Change.STYLE)
                notifySelectEvent(StyleClickEntity.SelectDone)
            }.onFailure {
                throwMessage(stringProvider.getErrorMessage())
                L.e(it)
            }
        }
    }

    fun clickSkip() {
        notifySelectEvent(StyleClickEntity.Skip)
    }

    private fun initSelectedStyle() {
        selectedCategory.clear()
        allCategories.map { large ->
            large.middleCategories.map { middle ->
                if (middle.smallCategories.all { it.isSelected.get() }) {
                    selectedCategory.add(middle.smallCategories.first())
                } else {
                    middle.smallCategories.map { small ->
                        if (small.isSelected.get()) {
                            selectedCategory.add(small)
                        }
                    }
                }

            }
        }
        setMaxSelectedCount()
        notifyActionEvent(StyleActionEntity.UpdateSelectedStyleList(selectedCategory))
    }


    private fun setMaxSelectedCount() {
        viewState.setIsMaxSelected(
            isEmpty = selectedCategory.isEmpty(),
            size = selectedCategory.size,
            maxCount = MAX_STYLE_COUNT,
        )
    }
}