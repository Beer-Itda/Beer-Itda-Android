package com.ddd4.synesthesia.beer.presentation.ui.filter.style.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ddd4.synesthesia.beer.data.Result
import com.ddd4.synesthesia.beer.data.model.filter.style.StyleLargeCategories
import com.ddd4.synesthesia.beer.domain.repository.BeerRepository
import com.ddd4.synesthesia.beer.domain.usecase.filter.style.GetStyleUseCase
import com.ddd4.synesthesia.beer.presentation.base.BaseViewModel
import com.ddd4.synesthesia.beer.presentation.base.entity.ItemClickEntity
import com.ddd4.synesthesia.beer.presentation.ui.filter.style.entity.FilterActionEntity
import com.ddd4.synesthesia.beer.presentation.ui.filter.style.entity.FilterClicklEntity
import com.ddd4.synesthesia.beer.presentation.ui.filter.style.item.large.StyleLargeItemMapper
import com.ddd4.synesthesia.beer.presentation.ui.filter.style.item.large.StyleLargeItemViewModel
import com.ddd4.synesthesia.beer.presentation.ui.filter.style.item.middle.StyleMiddleItemViewModel
import com.ddd4.synesthesia.beer.presentation.ui.filter.style.item.small.StyleSmallItemViewModel
import com.ddd4.synesthesia.beer.presentation.ui.filter.style.view.StyleStringProvider
import com.ddd4.synesthesia.beer.presentation.ui.filter.style.view.StyleViewState
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class StyleViewModel @ViewModelInject constructor(
    private val styleUseCase: GetStyleUseCase,
    private val stringProvider: StyleStringProvider
) : BaseViewModel() {

    companion object {
        const val MAX_STYLE_COUNT = 3
    }

    val viewState = StyleViewState()

    private val selectedStyleList = mutableListOf<StyleSmallItemViewModel>()
    private val currentMiddleCategory = mutableListOf<StyleMiddleItemViewModel>()
    private val currentSmallCategory = mutableListOf<StyleSmallItemViewModel>()

    private lateinit var allCategories: List<StyleLargeItemViewModel>

    fun init() {
        statusLoading()
        viewModelScope.launch {
            styleUseCase.execute { response ->
                when (response) {
                    is Result.Success -> {
                        allCategories = StyleLargeItemMapper.getStyles(
                            list = response.data,
                            eventNotifier = this@StyleViewModel
                        )
                        currentMiddleCategory.addAll(allCategories[0].middleCategories)
                        notifyActionEvent(FilterActionEntity.UpdateLarge(allCategories))
                        notifyActionEvent(FilterActionEntity.UpdateMiddle(currentMiddleCategory))
                        statusSuccess()
                    }
                    is Result.NoContents -> {
                        // do noting
                    }
                    is Result.Error -> {
                        // TODO do something...
                    }
                }
            }
        }
    }

    fun load(position: Int) {
        currentMiddleCategory.clear()
        currentMiddleCategory.addAll(allCategories[position].middleCategories)
        notifyActionEvent(FilterActionEntity.UpdateMiddle(currentMiddleCategory))
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
        currentSmallCategory.addAll(currentMiddleCategory[position].smallCategories)
        notifyActionEvent(FilterActionEntity.UpdateSmall(currentSmallCategory))
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
                        StyleStringProvider.Code.MAX
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