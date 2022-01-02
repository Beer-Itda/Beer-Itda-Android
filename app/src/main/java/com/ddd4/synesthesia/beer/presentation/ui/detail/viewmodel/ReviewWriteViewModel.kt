package com.ddd4.synesthesia.beer.presentation.ui.detail.viewmodel

import androidx.hilt.Assisted
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.ddd4.synesthesia.beer.presentation.base.BaseViewModel
import com.ddd4.synesthesia.beer.presentation.ui.detail.entity.ReviewWriteActionEntity
import com.ddd4.synesthesia.beer.presentation.ui.detail.entity.ReviewWriteClickEntity
import com.ddd4.synesthesia.beer.presentation.ui.detail.view.ReviewWriteBottomSheetDialogFragment
import com.ddd4.synesthesia.beer.util.KEY_BEER_ID
import com.hjiee.core.ext.orDefault
import com.hjiee.core.ext.orZero
import com.hjiee.core.manager.Change
import com.hjiee.core.manager.DataChangeManager
import com.hjiee.core.util.log.L
import com.hjiee.domain.usecase.review.PostReviewUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewWriteViewModel @Inject constructor(
    private val useCase: PostReviewUseCase,
    @Assisted private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val initialContent by lazy {
        (savedStateHandle.get(ReviewWriteBottomSheetDialogFragment.KEY_REVIEW_CONTENT) as? String).orEmpty()
    }
    private val initialStarScore by lazy {
        (savedStateHandle.get(ReviewWriteBottomSheetDialogFragment.KEY_REVIEW_STAR) as? Float)
            .orDefault(0.5f)
    }

    val content = MutableLiveData("")
    val starScore = MutableLiveData(0.5f)

    val minContentLength = 0
    val maxContentLength = 300

    private val beerId by lazy { (savedStateHandle.get(KEY_BEER_ID) as? Int).orZero() }

    fun postReview() {
        viewModelScope.launch {
            runCatching {
                useCase.execute(
                    beerId = beerId,
                    starScore = starScore.value.orDefault(0.5f),
                    content = content.value.orEmpty()
                )
            }.onSuccess {
                DataChangeManager.changed(Change.REVIEW)
                notifyActionEvent(ReviewWriteActionEntity.Success)
            }.onFailure {
                if (it.message.isNullOrEmpty().not()) {
                    throwMessage(it.message.orEmpty())
                }
                L.e(it)
            }
        }
    }

    fun isReviewChanged(): Boolean =
        initialStarScore == starScore.value && initialContent == content.value


    fun clickGuide() {
        notifySelectEvent(ReviewWriteClickEntity.LevelGuide)
    }
}