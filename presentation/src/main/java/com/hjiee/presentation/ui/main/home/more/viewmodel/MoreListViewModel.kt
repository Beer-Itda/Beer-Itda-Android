package com.hjiee.presentation.ui.main.home.more.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.hjiee.presentation.base.BaseViewModel
import com.hjiee.presentation.commom.entity.BeerClickEntity
import com.hjiee.presentation.ui.common.beer.item.BeerItemViewModel
import com.hjiee.presentation.ui.common.beer.item.BeerItemViewModelMapper.getBeerItemViewModel
import com.hjiee.presentation.ui.main.home.main.view.HomeBeerRecommendType
import com.hjiee.presentation.ui.main.home.more.entity.MoreListActionEntity
import com.hjiee.presentation.ui.main.home.more.footer.MoreItemLoadingViewModel
import com.hjiee.presentation.ui.main.home.more.item.IMoreListViewModel
import com.hjiee.presentation.ui.main.home.more.item.MoreListItemViewModel
import com.hjiee.presentation.ui.main.home.more.view.MoreListActivity.Companion.KEY_LIKE_SORT
import com.hjiee.presentation.ui.main.home.more.view.MoreListActivity.Companion.KEY_LIKE_TITLE
import com.hjiee.presentation.ui.main.home.more.view.MoreListActivity.Companion.KEY_LIKE_TYPE
import com.hjiee.presentation.util.sort.SortType
import com.hjiee.core.event.entity.ItemClickEntity
import com.hjiee.core.ext.orFalse
import com.hjiee.domain.entity.DomainEntity
import com.hjiee.domain.usecase.beer.GetRandomRecommendUseCase
import com.hjiee.domain.usecase.beer.GetSelectedAromaBeerUseCase
import com.hjiee.domain.usecase.beer.GetSelectedStyleBeerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class MoreListViewModel @Inject constructor(
    private val style: GetSelectedStyleBeerUseCase,
    private val aroma: GetSelectedAromaBeerUseCase,
    private val randomRecommend: GetRandomRecommendUseCase,
    private val savedState: SavedStateHandle
) : BaseViewModel() {

    private val _title = MutableLiveData<String>()
    val title: LiveData<String> get() = _title

    private val _isLoadMore = MutableLiveData<Boolean>()
    val isLoadMore: LiveData<Boolean> get() = _isLoadMore

    private val _isRefresh = MutableLiveData<Boolean>()
    val isRefresh: LiveData<Boolean> get() = _isRefresh

    private val sortType: SortType? by lazy { savedState.get(KEY_LIKE_SORT) }
    private val type: HomeBeerRecommendType? by lazy { savedState.get(KEY_LIKE_TYPE) }

    private val _page = MutableLiveData<DomainEntity.Page?>()
    val page: LiveData<DomainEntity.Page?> get() = _page

    private val items = mutableListOf<IMoreListViewModel>()
    private val loadMoreItem = MoreItemLoadingViewModel()

    init {
        _title.value = savedState.get(KEY_LIKE_TITLE)
    }

    fun load() {
        viewModelScope.launch(errorHandler) {
            val response = when (type) {
                HomeBeerRecommendType.AROMA -> {
                    aroma.execute(page.value?.nextPage).let { result ->
                        _page.value = result?.page
                        result?.data.getBeerItemViewModel(eventNotifier = this@MoreListViewModel)
                            .map {
                                MoreListItemViewModel(
                                    beer = it,
                                    eventNotifier = this@MoreListViewModel
                                )
                            }
                    }
                }
                HomeBeerRecommendType.STYLE -> {
                    style.execute(page.value?.nextPage).let { result ->
                        _page.value = result?.page
                        result?.data.getBeerItemViewModel(eventNotifier = this@MoreListViewModel)
                            .map {
                                MoreListItemViewModel(
                                    beer = it,
                                    eventNotifier = this@MoreListViewModel
                                )
                            }
                    }
                }
                HomeBeerRecommendType.RANDOM -> {
                    randomRecommend.execute(page.value?.nextPage).let { result ->
                        _page.value = result?.page
                        result?.data.getBeerItemViewModel(eventNotifier = this@MoreListViewModel)
                            .map {
                                MoreListItemViewModel(
                                    beer = it,
                                    eventNotifier = this@MoreListViewModel
                                )
                            }
                    }
                }
                else -> {
                    null
                }
            }
            items.addAll(response.orEmpty())
            removeLoadingProgress()

            _isRefresh.value = false
            _isLoadMore.value = false
            notifyActionEvent(MoreListActionEntity.UpdateList(items))
        }
    }

    override fun notifySelectEvent(entity: ItemClickEntity) {
        when (entity) {
            is BeerClickEntity.ClickFavorite -> {
                fetchFavorite(entity.beer)
            }
            is BeerClickEntity.ClickBeer -> {
                notifyActionEvent(MoreListActionEntity.MoveToDetail(entity.beer.id))
            }
        }
    }

    private fun fetchFavorite(beer: BeerItemViewModel) {
        viewModelScope.launch(errorHandler) {
            beer.updateFavorite()
        }
    }

    fun loadMore() {
        if (_isLoadMore.value.orFalse().not()) {
            if (_page.value?.hasNext().orFalse()) {
                _isLoadMore.value = true
                addLoadingProgress()
                load()
            }
        }
    }

    fun refresh() {
        _isRefresh.value = true
        _page.value?.clear()
        items.clear()
        load()
        notifyActionEvent(MoreListActionEntity.Refresh)
    }

    private fun addLoadingProgress() {
        items.add(loadMoreItem)
        notifyActionEvent(MoreListActionEntity.UpdateList(items))
    }

    private fun removeLoadingProgress() {
        items.remove(loadMoreItem)
        notifyActionEvent(MoreListActionEntity.UpdateList(items))
    }
}