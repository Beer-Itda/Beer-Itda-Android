package com.ddd4.synesthesia.beer.presentation.ui.detail.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ddd4.synesthesia.beer.data.model.Beer
import com.ddd4.synesthesia.beer.domain.repository.BeerRepository
import com.ddd4.synesthesia.beer.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class DetailViewModel @ViewModelInject constructor(
    private val beerRepository: BeerRepository
) : BaseViewModel() {

    private val _beer = MutableLiveData<Beer>()
    val beer : LiveData<Beer> get() = _beer

    init {
        fetchBeer()
    }

    private fun fetchBeer() {
        viewModelScope.launch {
           _beer.postValue(beerRepository.getBeer())
        }
    }
}