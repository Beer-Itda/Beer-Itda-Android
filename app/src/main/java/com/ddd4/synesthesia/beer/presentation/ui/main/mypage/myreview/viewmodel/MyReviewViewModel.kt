package com.ddd4.synesthesia.beer.presentation.ui.main.mypage.myreview.viewmodel

import androidx.lifecycle.viewModelScope
import com.ddd4.synesthesia.beer.presentation.base.BaseViewModel
import com.ddd4.synesthesia.beer.presentation.ui.main.mypage.myreview.item.MyReviewItemMapper.getMapperItem
import com.ddd4.synesthesia.beer.presentation.ui.main.mypage.myreview.item.MyReviewItemViewModel
import com.ddd4.synesthesia.beer.presentation.ui.main.mypage.myreview.model.MyReviewActionEntity
import com.hjiee.core.util.log.L
import com.hjiee.domain.usecase.mypage.MyReviewUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyReviewViewModel @Inject constructor(
    private val myReviewUseCase: MyReviewUseCase
) : BaseViewModel() {

    private var myReview: List<MyReviewItemViewModel> = emptyList()

    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
            runCatching {
                myReview = myReviewUseCase.execute().getMapperItem(this@MyReviewViewModel)
            }.onSuccess {
                notifyActionEvent(MyReviewActionEntity.UpdateUi(myReview))
            }.onFailure {
                L.e(it)
            }
        }
    }
}