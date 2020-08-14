package com.ddd4.synesthesia.beer.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ddd4.synesthesia.beer.base.BaseViewModel
import com.ddd4.synesthesia.beer.data.model.Beer
import com.ddd4.synesthesia.beer.data.repository.BeerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class HomeViewModel(
    private val repository: BeerRepository = BeerRepository()
) : BaseViewModel() {

    private val _beerList = MutableLiveData<List<Beer>>()
    val beerList: LiveData<List<Beer>>
        get() = _beerList

    init {
        Timber.d("View Model Initialize: ")
        fetchBeerList()
    }

    private fun fetchBeerList() {
        viewModelScope.launch(Dispatchers.IO) {
            _beerList.postValue(repository.getBeerList())
        }
    }

}