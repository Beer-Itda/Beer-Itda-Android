package com.hjiee.presentation.ui.detail.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.hjiee.presentation.R
import com.hjiee.presentation.databinding.ActivityBeerDetailBinding
import com.hjiee.presentation.base.BaseActivity
import com.hjiee.presentation.commom.entity.BeerClickEntity
import com.hjiee.presentation.ui.common.review.model.ReviewItemSelectEntity
import com.hjiee.presentation.ui.detail.adapter.DetailAdapter
import com.hjiee.presentation.ui.detail.entity.BeerDetailActionEntity
import com.hjiee.presentation.ui.detail.entity.BeerDetailItemSelectEntity
import com.hjiee.presentation.ui.detail.view.ReviewWriteBottomSheetDialogFragment.Companion.getBundle
import com.hjiee.presentation.ui.detail.viewmodel.BeerDetailViewModel
import com.hjiee.presentation.ui.review.view.ReviewListActivity
import com.hjiee.presentation.util.KEY_BEER_ID
import com.hjiee.presentation.util.ext.*
import com.hjiee.core.event.entity.ActionEntity
import com.hjiee.core.event.entity.ItemClickEntity
import com.hjiee.core.ext.*
import com.hjiee.core.observer.observeChangedFavoriteState
import com.hjiee.core.observer.observeReviewRegistered
import com.hjiee.domain.entity.DomainEntity.Review.Companion.DEFAULT_STAR
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BeerDetailActivity : BaseActivity<ActivityBeerDetailBinding>(R.layout.activity_beer_detail) {

    private val detailViewModel by viewModels<BeerDetailViewModel>()
    private val detailAdapter by lazy { DetailAdapter() }
    private val beerId by lazy { (intent.extras?.get(KEY_BEER_ID) as? Int).orZero() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBind()
        initObserver()
        detailViewModel.load()
    }

    override fun initBind() {
        binding.apply {
            viewModel = detailViewModel
            detailAdapter = this@BeerDetailActivity.detailAdapter
            includeToolbar.toolbar.setNavigationOnClickListener { finish() }
        }
    }

    override fun initObserver() {
        observeHandledEvent(detailViewModel.event.select) {
            handleSelectEvent(it)
        }
        observeHandledEvent(detailViewModel.event.action) {
            handleActionEvent(it)
        }
        // 스택이 쌓인상태에서 좋아요 상태 변경시 새로고침
        observeChangedFavoriteState {
            detailViewModel.load()
        }
        // 리뷰 작성 후 새로고침
        observeReviewRegistered {
            detailViewModel.load()
        }
    }

    override fun handleActionEvent(entity: ActionEntity) {
        when (entity) {
            is BeerDetailActionEntity.UpdateUi -> {
                detailAdapter.clear()
                detailAdapter.addAll(entity.items)
            }
            is BeerDetailActionEntity.Toast -> {
                Toast.makeText(this, entity.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun handleSelectEvent(entity: ItemClickEntity) {
        when (entity) {
            is BeerClickEntity.ClickBeer -> {
                start<BeerDetailActivity>(intent = getIntent(this, entity.beer.id.orZero()))
            }
            is BeerDetailItemSelectEntity.ReviewAll -> {
                start<ReviewListActivity>(
                    ReviewListActivity.getIntent(
                        context = this,
                        beerId = entity.beerId,
                        reviewCount = entity.reviewCount
                    )
                )
            }
            is ReviewItemSelectEntity.WriteReview -> {
                reviewWriteBottomSheetDialog()
            }
            is ReviewItemSelectEntity.EditReview -> {
                ReviewEditBottomSheetDialog(
                    context = this,
                    modifyAction = { reviewWriteBottomSheetDialog() },
                    deleteAction = { detailViewModel.reviewDelete() }
                ).show()
            }
        }
    }

    private fun reviewWriteBottomSheetDialog() {
        ReviewWriteBottomSheetDialogFragment().apply {
            val isModify = detailViewModel.myReview.value?.reviewId.orZero() != 0
            arguments = getBundle(
                beerId = beerId,
                content = detailViewModel.myReview.value?.content,
                star = detailViewModel.myReview.value?.star.orDefault(DEFAULT_STAR),
                isModify = isModify
            )
            setCallbackListener {
                detailViewModel.load()
                if (isModify) {
                    context?.showToast(getString(R.string.success_modified_review))
                } else {
                    context?.showToast(getString(R.string.success_registered_review))
                }
            }
            show(supportFragmentManager, "")
        }
    }

    companion object {
        const val REQ_CODE_DETAIL = 10

        fun getIntent(
            context: Context,
            beerId: Int
        ): Intent {
            return Intent(context, BeerDetailActivity::class.java).apply {
                putExtra(KEY_BEER_ID, beerId)
            }
        }
    }

}