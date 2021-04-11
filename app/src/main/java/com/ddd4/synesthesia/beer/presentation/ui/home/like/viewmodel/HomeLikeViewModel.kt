package com.ddd4.synesthesia.beer.presentation.ui.home.like.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.ddd4.synesthesia.beer.domain.repository.BeerRepository
import com.ddd4.synesthesia.beer.ext.orFalse
import com.ddd4.synesthesia.beer.presentation.base.BaseViewModel
import com.ddd4.synesthesia.beer.presentation.base.entity.ItemClickEntity
import com.ddd4.synesthesia.beer.presentation.commom.entity.BeerClickEntity
import com.ddd4.synesthesia.beer.presentation.ui.common.beer.item.BeerItemViewModel
import com.ddd4.synesthesia.beer.presentation.ui.common.filter.BeerFilter
import com.ddd4.synesthesia.beer.presentation.ui.home.like.entity.HomeLikeActionEntity
import com.ddd4.synesthesia.beer.presentation.ui.home.like.footer.HomeLikeListItemLoadingViewModel
import com.ddd4.synesthesia.beer.presentation.ui.home.like.item.HomeLikeListModelMapper
import com.ddd4.synesthesia.beer.presentation.ui.home.like.item.IHomeLikeViewModel
import com.ddd4.synesthesia.beer.presentation.ui.home.like.view.HomeLikeActivity.Companion.KEY_LIKE_FILTER
import com.ddd4.synesthesia.beer.presentation.ui.home.like.view.HomeLikeActivity.Companion.KEY_LIKE_SORT
import com.ddd4.synesthesia.beer.presentation.ui.home.like.view.HomeLikeActivity.Companion.KEY_LIKE_TITLE
import com.ddd4.synesthesia.beer.presentation.ui.home.like.view.HomeLikeActivity.Companion.KEY_LIKE_TYPE
import com.ddd4.synesthesia.beer.presentation.ui.home.main.view.HomeStringProvider
import com.ddd4.synesthesia.beer.util.sort.SortType
import kotlinx.coroutines.launch

class HomeLikeViewModel @ViewModelInject constructor(
    private val beerRepository: BeerRepository,
    @Assisted private val savedState: SavedStateHandle
) : BaseViewModel() {

    private val _data = MutableLiveData<List<IHomeLikeViewModel>>()
    val data: LiveData<List<IHomeLikeViewModel>> get() = _data

    private val _title = MutableLiveData<String>()
    val title: LiveData<String> get() = _title

    private val _isLoadMore = MutableLiveData<Boolean>()
    val isLoadMore: LiveData<Boolean> get() = _isLoadMore

    private val _isRefresh = MutableLiveData<Boolean>()
    val isRefresh: LiveData<Boolean> get() = _isRefresh

    private val _cursor = MutableLiveData(0)
    val cursor: LiveData<Int> get() = _cursor

    private val sortType: SortType? by lazy { savedState.get(KEY_LIKE_SORT) }
    private val filter: BeerFilter? by lazy { savedState.get(KEY_LIKE_FILTER) }
    private val type: HomeStringProvider.Code? by lazy { savedState.get(KEY_LIKE_TYPE) }

    init {
        _title.value = savedState.get(KEY_LIKE_TITLE)
    }

    fun load() {
        viewModelScope.launch {
            val response = when (type) {
                HomeStringProvider.Code.AROMA -> {
                    beerRepository.getBeerList(
                        sortType = sortType?.value,
                        filter = BeerFilter(aromaFilter = filter?.aromaFilter),
                        cursor = cursor.value
                    )
                }
                HomeStringProvider.Code.STYLE -> {
                    beerRepository.getBeerList(
                        sortType = sortType?.value,
                        filter = BeerFilter(styleFilter = filter?.styleFilter),
                        cursor = cursor.value
                    )
                }
                else -> {
                    beerRepository.getBeerList(
                        sortType = null,
                        filter = null,
                        cursor = cursor.value
                    )
                }
            }
            removeLoadingProgress()
            val data =
                HomeLikeListModelMapper.getMapper(response?.beers.orEmpty(), this@HomeLikeViewModel)

            if (_isLoadMore.value.orFalse()) {
                _data.value = _data.value?.toMutableList()?.run {
                    addAll(data)
                    this
                }
            } else {
                _data.value = data
            }

            _isRefresh.value = false
            _isLoadMore.value = false
            _cursor.value = response?.nextCursor
            notifyActionEvent(HomeLikeActionEntity.UpdateList(_data.value))
        }
    }

    override fun notifySelectEvent(entity: ItemClickEntity) {
        when (entity) {
            is BeerClickEntity.SelectFavorite2 -> {
                fetchFavorite(entity.beer)
            }
            is BeerClickEntity.SelectBeer -> {
                notifyActionEvent(HomeLikeActionEntity.MoveToDetail(entity.beer.id))
            }
        }
    }

    private fun fetchFavorite(beer: BeerItemViewModel) {
        viewModelScope.launch {
            beer.updateFavorite()
            beerRepository.postFavorite(beer.id, beer.isFavorite.get())
        }
    }

    fun loadMore() {
        if (_isLoadMore.value.orFalse().not()) {
            cursor.value?.let {
                _isLoadMore.value = true
                addLoadingProgress()
                load()
            }
        }
    }

    fun refresh() {
        _isRefresh.value = true
        _cursor.value = 0
        load()
        notifyActionEvent(HomeLikeActionEntity.Refresh)
    }

    private fun addLoadingProgress() {
        _data.value = _data.value?.toMutableList()?.run {
            addAll(arrayListOf(HomeLikeListItemLoadingViewModel()))
            this
        }
        notifyActionEvent(HomeLikeActionEntity.UpdateList(_data.value))
    }

    private fun removeLoadingProgress() {
        _data.value = _data.value?.toMutableList()?.run {
            if (_data.value.orEmpty().size > 1) {
                removeAt(_data.value.orEmpty().size - 1)
            }
            this
        }
        notifyActionEvent(HomeLikeActionEntity.UpdateList(_data.value))
    }
}