package com.ddd4.synesthesia.beer.presentation.ui.main.mypage.review.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ddd4.synesthesia.beer.presentation.base.BaseViewModel
import com.hjiee.domain.entity.DomainEntity
import com.hjiee.domain.repository.BeerRepository
import kotlinx.coroutines.launch

class MyReviewViewModel @ViewModelInject constructor(
    private val beerRepository: BeerRepository
) : BaseViewModel() {

    private val _myReviews = MutableLiveData<List<DomainEntity.Review>>()
    val myReviews: LiveData<List<DomainEntity.Review>> get() = _myReviews

    init {
        review()
    }

    fun review() {
        viewModelScope.launch(errorHandler) {
//            val result = (beerRepository.getReview())
//            _myReviews.postValue(result.results)
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}