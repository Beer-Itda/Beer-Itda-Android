package com.hjiee.presentation.ui.main.mypage.favorite.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hjiee.presentation.base.BaseViewModel
import com.hjiee.presentation.commom.entity.BeerClickEntity
import com.hjiee.presentation.ui.common.beer.item.BeerItemViewModelMapper.getBeerItemViewModel
import com.hjiee.presentation.ui.main.mypage.favorite.item.MyFavoriteItemViewModel
import com.hjiee.presentation.ui.main.mypage.favorite.model.MyFavoriteActionEntity
import com.hjiee.core.event.entity.ItemClickEntity
import com.hjiee.core.ext.orFalse
import com.hjiee.core.util.log.L
import com.hjiee.domain.entity.DomainEntity
import com.hjiee.domain.usecase.beer.PostFavoriteUseCase
import com.hjiee.domain.usecase.mypage.MyFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyFavoriteViewModel @Inject constructor(
    private val useCase: MyFavoriteUseCase,
    private val favorite: PostFavoriteUseCase
) : BaseViewModel() {

    private val _isRefresh = MutableLiveData<Boolean>(false)
    val isRefresh: LiveData<Boolean> get() = _isRefresh

    private val _isLoadMore = MutableLiveData<Boolean>(false)
    val isLoadMore: LiveData<Boolean> get() = _isLoadMore

    private val _page = MutableLiveData<DomainEntity.Page?>()
    val page: LiveData<DomainEntity.Page?> get() = _page

    private val items = mutableListOf<MyFavoriteItemViewModel>()

    init {
        load()
    }

    fun load() {
        viewModelScope.launch(errorHandler) {
            runCatching {
                useCase.execute(_page.value?.nextPage)
            }.onSuccess { response ->
                _page.value = response.page
                val beerList = response.data.getBeerItemViewModel(this@MyFavoriteViewModel)
                val result = beerList.map { beer -> MyFavoriteItemViewModel(beer) }

                if (_isLoadMore.value == true) {
                    items.addAll(result)
                } else {
                    items.clear()
                    items.addAll(result)
                }

                notifyActionEvent(MyFavoriteActionEntity.UpdateUi(items))
            }.onFailure {
                L.e(it)
            }
            setRefresh(false)
            setLoadMore(false)
        }
    }

    fun refresh() {
        setRefresh(true)
        _page.value?.clear()
        notifyActionEvent(MyFavoriteActionEntity.Refresh)
        load()
    }

    fun loadMore() {
        if (_page.value?.hasNext() == true && !_isLoadMore.value.orFalse()) {
            setLoadMore(true)
            load()
        }
    }

    private fun fetchFavorite(id: Int) {
        viewModelScope.launch(errorHandler) {
            runCatching {
                favorite.execute(id)
            }.onFailure {
                L.e(it)
            }
        }
    }

    override fun handleSelectEvent(entity: ItemClickEntity) {
        when (entity) {
            is BeerClickEntity.ClickFavorite -> {
                fetchFavorite(entity.beer.id)
            }
        }
    }

    private fun setRefresh(status: Boolean) {
        _isRefresh.value = status
    }

    private fun setLoadMore(status: Boolean) {
        _isLoadMore.value = status
    }

}