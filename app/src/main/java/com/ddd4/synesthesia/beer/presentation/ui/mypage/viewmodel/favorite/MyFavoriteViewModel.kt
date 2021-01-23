package com.ddd4.synesthesia.beer.presentation.ui.mypage.viewmodel.favorite

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ddd4.synesthesia.beer.data.model.Beer
import com.ddd4.synesthesia.beer.domain.repository.BeerRepository
import com.ddd4.synesthesia.beer.presentation.base.BaseViewModel
import com.ddd4.synesthesia.beer.presentation.base.entity.ItemClickEntity
import com.ddd4.synesthesia.beer.presentation.commom.BeerClickEntity
import kotlinx.coroutines.launch

class MyFavoriteViewModel @ViewModelInject constructor(
    private val beerRepository: BeerRepository
): BaseViewModel() {

    private val _myFavorites = MutableLiveData<List<Beer?>>()
    val myFavorites : LiveData<List<Beer?>> get() = _myFavorites

    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
            _myFavorites.value = beerRepository.getFavorite().results?.map {
                    it.beer?.apply {
                        setFavorite()
                        eventNotifier = this@MyFavoriteViewModel
                    }
                }?.toList().orEmpty()
        }
    }

    private fun fetchFavorite(beer : Beer) {
        viewModelScope.launch {
            beer.updateFavorite()
            beerRepository.postFavorite(beer.id, beer.isFavorite.get())
        }
    }

    override fun handleSelectEvent(entity: ItemClickEntity) {
        when(entity) {
            is BeerClickEntity.SelectFavorite -> {
                fetchFavorite(entity.beer)
                load()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}