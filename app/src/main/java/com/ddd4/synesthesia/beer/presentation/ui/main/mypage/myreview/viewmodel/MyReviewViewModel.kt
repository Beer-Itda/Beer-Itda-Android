package com.ddd4.synesthesia.beer.presentation.ui.main.mypage.myreview.viewmodel

import androidx.lifecycle.viewModelScope
import com.ddd4.synesthesia.beer.presentation.base.BaseViewModel
import com.ddd4.synesthesia.beer.presentation.ui.main.mypage.myreview.item.MyReviewItemMapper.getMapperItem
import com.ddd4.synesthesia.beer.presentation.ui.main.mypage.myreview.item.MyReviewItemViewModel
import com.ddd4.synesthesia.beer.presentation.ui.main.mypage.myreview.model.MyReviewActionEntity
import com.hjiee.core.ext.orFalse
import com.hjiee.core.util.log.L
import com.hjiee.domain.entity.DomainEntity
import com.hjiee.domain.usecase.mypage.MyReviewUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyReviewViewModel @Inject constructor(
    private val myReviewUseCase: MyReviewUseCase
) : BaseViewModel() {

    private val myReview = mutableListOf<MyReviewItemViewModel>()
    private var page: DomainEntity.Page? = null

    init {
        load()
    }

    fun load(next: Int? = null) {
        statusLoading()
        viewModelScope.launch {
            runCatching {
                myReviewUseCase.execute(next)
            }.onSuccess { response ->
                myReview.addAll(response.getMapperItem(this@MyReviewViewModel))
                page = response.page
                notifyActionEvent(MyReviewActionEntity.UpdateUi(myReview))
                statusSuccess()
            }.onFailure {
                L.e(it)
                statusFailure()
            }
        }
    }

    fun loadMore() {
        if (page?.hasNext().orFalse()) {
            load(page?.nextPage)
        }
    }
}