package com.ddd4.synesthesia.beer.presentation.ui.main.search.viewmodel

import androidx.databinding.Observable
import androidx.databinding.ObservableBoolean
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ddd4.synesthesia.beer.presentation.base.BaseViewModel
import com.hjiee.core.event.entity.ItemClickEntity
import com.ddd4.synesthesia.beer.presentation.commom.entity.BeerClickEntity
import com.ddd4.synesthesia.beer.presentation.ui.main.search.model.SearchSelectEvent
import com.ddd4.synesthesia.beer.util.ext.ObservableExt.ObservableString
import com.hjiee.data.response.v2.BeerResponse
import com.hjiee.domain.repository.BeerRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SearchViewModel @ViewModelInject constructor(
    private val beerRepository: BeerRepository
) : BaseViewModel() {

    private var debounceJob: Job? = null

    val isLoadMore = ObservableBoolean(false)
    val searchText = ObservableString()
    val isTemplateVisible = ObservableBoolean(false)

    private val _beerList = MutableLiveData<List<BeerResponse>?>()
    val beerList: LiveData<List<BeerResponse>?> get() = _beerList

    val cursor = MutableLiveData(0)

    private val searchTextObserver = object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
            isTemplateVisible.set(false)
            cursor.value = null
            search()
        }
    }

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

    fun clearText() {
        reset()
        searchText.set("")
    }

    fun loadMore() {
        if (!isLoadMore.get()) {
            cursor.value?.let {
                isLoadMore.set(true)
//                _beerList.value =
//                    _beerList.value?.toMutableList()?.apply { addAll(listOf(Beer(id = -1))) }
                search()
            }
        }
    }

    fun refresh() {
        isLoadMore.set(false)
        cursor.value = null
        search()
    }

    fun search() {
        debounceJob?.cancel()
        if (searchText.get().isNullOrEmpty()) {
            reset()
            return
        }
        debounceJob = viewModelScope.launch(errorHandler) {
            delay(400L)
//            beerRepository.getSearch(
//                searchText.get().orEmpty(),
//                cursor.value
//            )?.result?.let { response ->
//                cursor.value = response.nextCursor
//                // 데이터 추가
//                if (isLoadMore.get()) {
//                    // 커서
//                    _beerList.value = (_beerList.value?.toMutableList()?.apply {
//                        _beerList.value?.let {
//                            if (it.isNotEmpty()) {
//                                removeAt(it.size - 1)
//                            }
//                        }
//                    })
//                    _beerList.value = (_beerList.value?.let { beers ->
//                        beers.toMutableList().apply {
//                            response.beers?.let { data ->
//                                val beers = data.map { beer ->
//                                    beer.setFavorite()
//                                    beer.eventNotifier = this@SearchViewModel
//                                    beer
//                                }
//                                addAll(beers)
//                            }
//                        }
//                    })
//                    isLoadMore.set(false)
//                } else {
//                    _beerList.value = response.beers?.map { beer ->
//                        beer.setFavorite()
//                        beer.eventNotifier = this@SearchViewModel
//                        beer
//                    }
//                }
//            }

        }
    }

    fun reset() {
        _beerList.value = emptyList()
        cursor.value = null
    }

    private fun fetchFavorite(beer: BeerResponse) {
        viewModelScope.launch(errorHandler) {
//            beer.updateFavorite()
//            beerRepository.postFavorite(beer.id, beer.isFavorite.get())
        }
    }

    override fun handleSelectEvent(entity: ItemClickEntity) {
        when (entity) {
            is BeerClickEntity.SelectFavorite -> {
//                fetchFavorite(entity.beer)
            }
        }
    }

    fun clickInquire() {
        notifySelectEvent(SearchSelectEvent.Inquire)
    }

    fun clickSearch() {
        isTemplateVisible.set(true)
    }

    override fun onCleared() {
        super.onCleared()
        searchText.removeOnPropertyChangedCallback(searchTextObserver)
    }
}