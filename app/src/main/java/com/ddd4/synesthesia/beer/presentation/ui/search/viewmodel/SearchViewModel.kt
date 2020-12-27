package com.ddd4.synesthesia.beer.presentation.ui.search.viewmodel

import androidx.databinding.Observable
import androidx.databinding.ObservableBoolean
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ddd4.synesthesia.beer.data.model.Beer
import com.ddd4.synesthesia.beer.domain.repository.BeerRepository
import com.ddd4.synesthesia.beer.ext.ObservableExt.ObservableString
import com.ddd4.synesthesia.beer.presentation.base.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SearchViewModel @ViewModelInject constructor(
    private val beerRepository: BeerRepository
) : BaseViewModel() {

    private var debounceJob : Job? = null

    val isLoadMore = ObservableBoolean(false)
    val searchText = ObservableString()
    val isTemplateVisible = ObservableBoolean(false)

    private val _beerList = MutableLiveData<List<Beer>?>()
    val beerList : LiveData<List<Beer>?> get() = _beerList

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
    }

    fun clearText() {
        reset()
        searchText.set("")
    }

    fun loadMore() {
        if(!isLoadMore.get()) {
            cursor.value?.let {
                isLoadMore.set(true)
                _beerList.value = _beerList.value?.toMutableList()?.apply { addAll(listOf(Beer(id = -1))) }
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
        if(searchText.get().isNullOrEmpty()) {
            reset()
            return
        }
        debounceJob = viewModelScope.launch {
            delay(300L)
            beerRepository.getSearch(searchText.get().orEmpty(),cursor.value)?.result?.let { response ->
                cursor.value = response.nextCursor
                // 데이터 추가
                if(isLoadMore.get()) {
                    // 커서
                    _beerList.value = (_beerList.value?.toMutableList()?.apply { _beerList.value?.let {
                        if(it.isNotEmpty()) {
                            removeAt(it.size-1) }
                        }
                    })
                    _beerList.value = (_beerList.value?.let { beers ->
                        beers.toMutableList().apply {
                            response.beers?.let { data ->
                                addAll(data)
                            }
                        }
                    })
                    isLoadMore.set(false)
                } else {
                    _beerList.value = response.beers
                }
            }

        }
    }

    fun reset() {
        _beerList.value = emptyList()
        cursor.value = null
    }


    override fun onCleared() {
        super.onCleared()
        searchText.removeOnPropertyChangedCallback(searchTextObserver)
    }
}