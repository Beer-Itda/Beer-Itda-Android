package com.ddd4.synesthesia.beer.presentation.ui.main.home.more.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.ddd4.synesthesia.beer.presentation.base.BaseViewModel
import com.hjiee.core.event.entity.ItemClickEntity
import com.ddd4.synesthesia.beer.presentation.commom.entity.BeerClickEntity
import com.ddd4.synesthesia.beer.presentation.ui.common.beer.item.BeerItemViewModel
import com.ddd4.synesthesia.beer.presentation.ui.common.filter.AromaProvider
import com.ddd4.synesthesia.beer.presentation.ui.common.filter.StyleProvider
import com.ddd4.synesthesia.beer.presentation.ui.main.home.main.view.HomeStringProvider
import com.ddd4.synesthesia.beer.presentation.ui.main.home.more.entity.MoreListActionEntity
import com.ddd4.synesthesia.beer.presentation.ui.main.home.more.footer.MoreItemLoadingViewModel
import com.ddd4.synesthesia.beer.presentation.ui.main.home.more.item.IMoreListViewModel
import com.ddd4.synesthesia.beer.presentation.ui.main.home.more.view.MoreListActivity.Companion.KEY_LIKE_SORT
import com.ddd4.synesthesia.beer.presentation.ui.main.home.more.view.MoreListActivity.Companion.KEY_LIKE_TITLE
import com.ddd4.synesthesia.beer.presentation.ui.main.home.more.view.MoreListActivity.Companion.KEY_LIKE_TYPE
import com.ddd4.synesthesia.beer.util.sort.SortType
import com.hjiee.domain.repository.BeerRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class MoreListViewModel @ViewModelInject constructor(
    private val beerRepository: BeerRepository,
    private val styleProvider: StyleProvider,
    private val aromaProvider: AromaProvider,
    @Assisted private val savedState: SavedStateHandle
) : BaseViewModel() {

    private val _data = MutableLiveData<List<IMoreListViewModel>>()
    val data: LiveData<List<IMoreListViewModel>> get() = _data

    private val _title = MutableLiveData<String>()
    val title: LiveData<String> get() = _title

    private val _isLoadMore = MutableLiveData<Boolean>()
    val isLoadMore: LiveData<Boolean> get() = _isLoadMore

    private val _isRefresh = MutableLiveData<Boolean>()
    val isRefresh: LiveData<Boolean> get() = _isRefresh

    private val _cursor = MutableLiveData(0)
    val cursor: LiveData<Int> get() = _cursor

    private val sortType: SortType? by lazy { savedState.get(KEY_LIKE_SORT) }
    private val type: HomeStringProvider.Code? by lazy { savedState.get(KEY_LIKE_TYPE) }

    init {
        _title.value = savedState.get(KEY_LIKE_TITLE)
    }

    fun load() {
        viewModelScope.launch(errorHandler) {
            val response = when (type) {
                HomeStringProvider.Code.AROMA -> {
//                    beerRepository.getBeerList(
//                        sortType = sortType?.value,
//                        aroma = aromaProvider.getNames(),
//                        cursor = cursor.value
//                    )
                }
                HomeStringProvider.Code.STYLE -> {
//                    beerRepository.getBeerList(
//                        sortType = sortType?.value,
//                        style = styleProvider.getNames(),
//                        cursor = cursor.value
//                    )
                }
                else -> {
//                    beerRepository.getBeerList(
//                        sortType = null,
//                        cursor = cursor.value
//                    )
                }
            }
            removeLoadingProgress()
//            val data =
//                response.getMapper(eventNotifier = this@MoreListViewModel)

//            if (_isLoadMore.value.orFalse()) {
//                _data.value = _data.value?.toMutableList()?.run {
//                    addAll(data)
//                    this
//                }
//            } else {
//                _data.value = data
//            }

            _isRefresh.value = false
            _isLoadMore.value = false
//            _cursor.value = response?.nextCursor
            notifyActionEvent(MoreListActionEntity.UpdateList(_data.value))
        }
    }

    override fun notifySelectEvent(entity: ItemClickEntity) {
        when (entity) {
            is BeerClickEntity.SelectFavorite2 -> {
                fetchFavorite(entity.beer)
            }
            is BeerClickEntity.SelectBeer -> {
                notifyActionEvent(MoreListActionEntity.MoveToDetail(entity.beer.id))
            }
        }
    }

    private fun fetchFavorite(beer: BeerItemViewModel) {
        viewModelScope.launch(errorHandler) {
            beer.updateFavorite()
//            beerRepository.postFavorite(beer.id, beer.isFavorite.get())
        }
    }

    fun loadMore() {
//        if (_isLoadMore.value.orFalse().not()) {
//            cursor.value?.let {
//                _isLoadMore.value = true
//                addLoadingProgress()
//                load()
//            }
//        }
    }

    fun refresh() {
        _isRefresh.value = true
        _cursor.value = 0
        load()
        notifyActionEvent(MoreListActionEntity.Refresh)
    }

    private fun addLoadingProgress() {
        _data.value = _data.value?.toMutableList()?.run {
            addAll(arrayListOf(MoreItemLoadingViewModel()))
            this
        }
        notifyActionEvent(MoreListActionEntity.UpdateList(_data.value))
    }

    private fun removeLoadingProgress() {
        _data.value = _data.value?.toMutableList()?.run {
            if (_data.value.orEmpty().size > 1) {
                removeAt(_data.value.orEmpty().size - 1)
            }
            this
        }
        notifyActionEvent(MoreListActionEntity.UpdateList(_data.value))
    }
}