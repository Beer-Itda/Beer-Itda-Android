package com.ddd4.synesthesia.beer.presentation.ui.filter.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ddd4.synesthesia.beer.data.model.filter.BeerLargeType
import com.ddd4.synesthesia.beer.data.model.filter.StyleList
import com.ddd4.synesthesia.beer.domain.repository.BeerRepository
import com.ddd4.synesthesia.beer.presentation.base.BaseViewModel
import com.ddd4.synesthesia.beer.presentation.base.entity.ItemClickEntity
import com.ddd4.synesthesia.beer.presentation.ui.filter.entity.FilterActionEntity
import com.ddd4.synesthesia.beer.presentation.ui.filter.entity.FilterClicklEntity
import com.ddd4.synesthesia.beer.presentation.ui.filter.item.StyleItemModel
import com.ddd4.synesthesia.beer.presentation.ui.filter.item.middle.StyleMiddleItemMapper
import com.ddd4.synesthesia.beer.presentation.ui.filter.item.middle.StyleMiddleItemViewModel
import com.ddd4.synesthesia.beer.presentation.ui.filter.item.small.StyleSmallItemViewModel
import com.ddd4.synesthesia.beer.presentation.ui.filter.view.FilterStringProvider
import com.ddd4.synesthesia.beer.presentation.ui.filter.view.FilterViewState
import kotlinx.coroutines.launch

class FilterViewModel @ViewModelInject constructor(
    private val repository: BeerRepository,
    private val stringProvider: FilterStringProvider
) : BaseViewModel() {

    companion object {
        const val MAX_STYLE_COUNT = 3
    }

    private val _styleData = MutableLiveData<StyleList>()
    val styleData: LiveData<StyleList> get() = _styleData

    private val currentMiddleCategory = mutableListOf<StyleMiddleItemViewModel>()
    private val currentSmallCategory = mutableListOf<StyleSmallItemViewModel>()
    private val selectedStyleList = mutableListOf<StyleSmallItemViewModel>()
    val viewState = FilterViewState()

    private val totalStyle by lazy {
        StyleItemModel(
            aleList = StyleMiddleItemMapper.getAle(_styleData.value, this@FilterViewModel),
            lagerList = StyleMiddleItemMapper.getLarger(_styleData.value, this@FilterViewModel),
            lambicList = StyleMiddleItemMapper.getLambic(_styleData.value, this@FilterViewModel),
            ectList = StyleMiddleItemMapper.getEtc(_styleData.value, this@FilterViewModel)
        )
    }

    fun init() {
        statusLoading()
        viewModelScope.launch {
            _styleData.value = repository.getAppConfig().result?.styleList
            currentMiddleCategory.addAll(totalStyle.aleList)
            notifyActionEvent(FilterActionEntity.UpdateList(currentMiddleCategory))
            statusSuccess()
        }
    }

    fun load(largeType: BeerLargeType) {
        selectedStyleList
        currentMiddleCategory.clear()
        when (largeType) {
            BeerLargeType.Ale -> {
                currentMiddleCategory.addAll(totalStyle.aleList)
            }
            BeerLargeType.Lager -> {
                currentMiddleCategory.addAll(totalStyle.lagerList)
            }
            BeerLargeType.Lambic -> {
                currentMiddleCategory.addAll(totalStyle.lambicList)
            }
            BeerLargeType.Etc -> {
                currentMiddleCategory.addAll(totalStyle.ectList)
            }
        }
        notifyActionEvent(FilterActionEntity.UpdateList(currentMiddleCategory))
    }

    fun loadFilterSet(position: Int) {
        currentMiddleCategory.filterIndexed { index, item ->
            item.isSelected.set(false)
            index == position
        }.map { item ->
            item.isSelected.set(true)
        }
        viewState.description.set(currentMiddleCategory[position].description)
        currentSmallCategory.clear()
        currentSmallCategory.addAll(currentMiddleCategory[position].list)
        notifyActionEvent(FilterActionEntity.UpdateStyleSet(currentSmallCategory))
    }

    override fun handleSelectEvent(entity: ItemClickEntity) {
        when (entity) {
            is FilterClicklEntity.AddStyle -> {
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

            is FilterClicklEntity.DeleteStyle -> {
                removeSelectedStyle(entity.style)
            }
        }
    }

    /**
     * 선택된 스타일 추가
     */
    private fun addSelectedStyle(item: StyleSmallItemViewModel) {
        resetCurrentSelectedStyle()
        selectedStyleList.add(0, item)
        setSelectedStatusChange(item, true)
        notifyActionEvent(FilterActionEntity.UpdateSelectedStyleList(selectedStyleList))
        setMaxSelectedCount()
    }

    /**
     * 선택된 스타일 삭제
     */
    private fun removeSelectedStyle(item: StyleSmallItemViewModel) {
        selectedStyleList.remove(item)
        setSelectedStatusChange(item, false)
        notifyActionEvent(FilterActionEntity.UpdateSelectedStyleList(selectedStyleList))
        setMaxSelectedCount()
    }

    /**
     * 선택된 스타일 모두 삭제
     */
    private fun removeAllCurrentStyleList() {
        selectedStyleList.removeAll(currentSmallCategory)
        notifyActionEvent(FilterActionEntity.UpdateSelectedStyleList(selectedStyleList))
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
        } else if (item.isAll && isContainsCurrentSelectedItem()) {
            addSelectedStyle(item)
        } else {
            notifyActionEvent(
                FilterActionEntity.ShowToast(
                    stringProvider.getStringRes(
                        FilterStringProvider.Code.MAX
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
            currentSmallCategory.forEachIndexed { index, style ->
                if (index != 0) {
                    removeSelectedStyle(style)
                }
                style.isSelected.set(isSelected)
            }
        } else {
            item.isSelected.set(isSelected)
        }
    }

    /**
     * 현재 소분류 스타일에 선택된 아이템이 있는지
     */
    private fun isContainsCurrentSelectedItem(): Boolean {
        return currentSmallCategory.find { style ->
            style.isSelected.get()
        }.let {
            it != null
        }
    }

    /**
     * 클릭한 아이템이 선택된 리스트에 포함되는지
     */
    private fun isContainsSelectedItem(
        item: StyleSmallItemViewModel
    ): Boolean {
        return selectedStyleList.find {
            it == item
        }.let {
            it != null
        }

    }

    fun clickDone() {
        notifySelectEvent(FilterClicklEntity.SelectDone(selectedStyleList))
    }


    private fun setMaxSelectedCount() {
        viewState.isSelectedEmpty.set(selectedStyleList.isEmpty())
        when {
            selectedStyleList.size == MAX_STYLE_COUNT -> {
                viewState.isMaxSelected.set(true)
            }
            selectedStyleList.size < MAX_STYLE_COUNT -> {
                viewState.isMaxSelected.set(false)
            }
        }
    }
}