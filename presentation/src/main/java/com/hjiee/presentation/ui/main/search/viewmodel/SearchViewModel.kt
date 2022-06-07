package com.hjiee.presentation.ui.main.search.viewmodel

import androidx.databinding.Observable
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hjiee.core.event.entity.ItemClickEntity
import com.hjiee.core.util.log.L
import com.hjiee.data.response.v2.BeerResponse
import com.hjiee.domain.entity.DomainEntity
import com.hjiee.domain.usecase.search.SearchUseCase
import com.hjiee.presentation.base.BaseViewModel
import com.hjiee.presentation.ui.main.search.item.ISearchViewModel
import com.hjiee.presentation.ui.main.search.item.SearchMapper.getItems
import com.hjiee.presentation.ui.main.search.item.loading.LoadingItemViewModel
import com.hjiee.presentation.ui.main.search.model.SearchActionEntity
import com.hjiee.presentation.ui.main.search.model.SearchSelectEvent
import com.hjiee.presentation.util.ext.ObservableExt.ObservableString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val useCase: SearchUseCase
) : BaseViewModel() {

    private var debounceJob: Job? = null

    val isLoadMore = ObservableBoolean(false)
    val isRefreshing = ObservableBoolean(false)
    val searchText = ObservableString()

    private val _page = MutableLiveData<DomainEntity.Page?>()
    private val page get() = _page.value ?: DomainEntity.Page()

    private val searchTextObserver = object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
            items.clear()
            page.clear()
            search()
        }
    }

    private val items = mutableListOf<ISearchViewModel>()
    private val loadingItem = LoadingItemViewModel()
    val isEmpty = ObservableBoolean(true)

    init {
        searchText.addOnPropertyChangedCallback(searchTextObserver)
        eventListen()
    }

    private fun eventListen() {
        viewModelScope.launch(errorHandler) {
//            EventFlow.subscribe<GlobalEvent>().collect { event ->
//                when (event) {
//                    is GlobalEvent.Favorite -> {
//                        beerList.value?.filter {
//                            it.id == event.beerId
//                        }?.map {
//                            it.updateFavorite()
//                            it
//                        }
//                    }
//                }
//            }
        }
    }

    fun loadMore() {
        if (!isLoadMore.get()) {
            if (page.hasNext()) {
                isLoadMore.set(true)
                items.add(loadingItem)
                notifyActionEvent(SearchActionEntity.UpdateList(items))
                search()
            }
        }
    }

    fun refresh() {
        notifyActionEvent(SearchActionEntity.Refresh)
        isRefreshing.set(true)
        items.clear()
        page.clear()
        search()
    }

    private fun search() {
        debounceJob?.cancel()
        if (searchText.get().isNullOrEmpty()) {
            clear()
            return
        }
        debounceJob = viewModelScope.launch {
            delay(400L)

            runCatching {
                useCase.execute(
                    word = searchText.get().orEmpty(),
                    page = page.nextPage,
                )
            }.onSuccess { result ->
                if (!isLoadMore.get()) {
                    items.clear()
                }
                isEmpty.set(result.data.isEmpty())
                items.addAll(
                    getItems(
                        searchText = searchText.get().orEmpty(),
                        items = result.data,
                        eventNotifier = this@SearchViewModel
                    )
                )
                items.remove(loadingItem)
                _page.value = result.page
                notifyActionEvent(SearchActionEntity.UpdateList(items))

                isRefreshing.set(false)
                isLoadMore.set(false)
            }.onFailure {
                isRefreshing.set(false)
                isLoadMore.set(false)
                L.e(it)
            }

        }
    }

    fun clear() {
        searchText.set("")
        isRefreshing.set(false)
        isLoadMore.set(false)
        isEmpty.set(true)
        page.clear()
        items.clear()
        notifyActionEvent(SearchActionEntity.UpdateList(items))
    }

    private fun fetchFavorite(beer: BeerResponse) {
        viewModelScope.launch {
            runCatching {

            }.onSuccess {

            }.onFailure {
                L.e(it)
            }
//            beer.updateFavorite()
//            beerRepository.postFavorite(beer.id, beer.isFavorite.get())
        }
    }

    override fun handleSelectEvent(entity: ItemClickEntity) {
        when (entity) {
        }
    }

    fun clickInquire() {
        notifySelectEvent(SearchSelectEvent.Inquire)
    }

    fun clickSearch() {

    }

    override fun onCleared() {
        super.onCleared()
        searchText.removeOnPropertyChangedCallback(searchTextObserver)
    }
}