package com.ddd4.synesthesia.beer.presentation.ui.review.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.ddd4.synesthesia.beer.presentation.base.BaseViewModel
import com.ddd4.synesthesia.beer.presentation.ui.common.review.ReviewItemViewModelMapper
import com.ddd4.synesthesia.beer.presentation.ui.review.model.ReviewListActionEntity
import com.ddd4.synesthesia.beer.presentation.ui.review.view.ReviewListActivity.Companion.KEY_REVIEW_COUNT
import com.ddd4.synesthesia.beer.presentation.ui.review.view.ReviewStringProvider
import com.ddd4.synesthesia.beer.util.KEY_BEER_ID
import com.hjiee.core.ext.orZero
import com.hjiee.core.util.log.L
import com.hjiee.domain.usecase.beer.GetReviewUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewListViewModel @Inject constructor(
    private val useCase: GetReviewUseCase,
    private val savedState: SavedStateHandle,
    private val stringProvider: ReviewStringProvider
) : BaseViewModel() {

    private val beerId = savedState.get<Int>(KEY_BEER_ID).orZero()
    private val reviewCount = savedState.get<Int>(KEY_REVIEW_COUNT).orZero()
    private val _title = MutableLiveData("")
    val title: LiveData<String> get() = _title

    init {
        _title.value = stringProvider.getTitle(reviewCount)
    }

    fun load() {
        viewModelScope.launch {
            runCatching {
                useCase.execute(beerId)
            }.onSuccess {
                val items = ReviewItemViewModelMapper.getReviewListItemViewModel(it.data)
                notifyActionEvent(ReviewListActionEntity.UpdateUi(items))
            }.onFailure {
                L.e(it)
            }
        }
    }

    private fun loadMore() {
        //TODO 더 불러오기
    }
}