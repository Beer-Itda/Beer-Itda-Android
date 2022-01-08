package com.ddd4.synesthesia.beer.presentation.ui.main.mypage.favorite.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ddd4.synesthesia.beer.presentation.base.BaseViewModel
import com.ddd4.synesthesia.beer.presentation.commom.entity.BeerClickEntity
import com.hjiee.core.event.entity.ItemClickEntity
import com.hjiee.domain.entity.DomainEntity.Beer
import com.hjiee.domain.repository.BeerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyFavoriteViewModel @Inject constructor(
    private val beerRepository: BeerRepository
) : BaseViewModel() {

    private val _myFavorites = MutableLiveData<List<Beer?>>()
    val myFavorites: LiveData<List<Beer?>> get() = _myFavorites

    private val _isRefresh = MutableLiveData<Boolean>(false)
    val isRefresh: LiveData<Boolean> get() = _isRefresh

    init {
        load()
    }

    fun load() {
        viewModelScope.launch(errorHandler) {
//            _myFavorites.value = beerRepository.getFavorite().results?.map {
//                it.beer?.apply {
//                    setFavorite()
//                    eventNotifier = this@MyFavoriteViewModel
//                }
//            }?.toList().orEmpty()
            setRefresh(false)
        }
    }

    fun refresh() {
        setRefresh(true)
        load()
    }

    private fun fetchFavorite(beer: Beer) {
        viewModelScope.launch(errorHandler) {
//            beer.updateFavorite()
//            beerRepository.postFavorite(beer.id, beer.isFavorite.get())
        }
    }

    override fun handleSelectEvent(entity: ItemClickEntity) {
        when (entity) {
        }
    }

    private fun setRefresh(status: Boolean) {
        _isRefresh.value = status
    }

    override fun onCleared() {
        super.onCleared()
    }
}