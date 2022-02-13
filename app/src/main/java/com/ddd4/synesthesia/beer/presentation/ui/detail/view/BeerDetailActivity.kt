package com.ddd4.synesthesia.beer.presentation.ui.detail.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.databinding.ActivityBeerDetailBinding
import com.ddd4.synesthesia.beer.presentation.base.BaseActivity
import com.ddd4.synesthesia.beer.presentation.commom.entity.BeerClickEntity
import com.ddd4.synesthesia.beer.presentation.ui.common.review.model.ReviewItemSelectEntity
import com.ddd4.synesthesia.beer.presentation.ui.detail.adapter.DetailAdapter
import com.ddd4.synesthesia.beer.presentation.ui.detail.entity.BeerDetailActionEntity
import com.ddd4.synesthesia.beer.presentation.ui.detail.entity.BeerDetailItemSelectEntity
import com.ddd4.synesthesia.beer.presentation.ui.detail.view.ReviewWriteBottomSheetDialogFragment.Companion.getBundle
import com.ddd4.synesthesia.beer.presentation.ui.detail.viewmodel.BeerDetailViewModel
import com.ddd4.synesthesia.beer.presentation.ui.review.view.ReviewListActivity
import com.ddd4.synesthesia.beer.util.KEY_BEER_ID
import com.ddd4.synesthesia.beer.util.ext.*
import com.hjiee.core.event.entity.ActionEntity
import com.hjiee.core.event.entity.ItemClickEntity
import com.hjiee.core.ext.*
import com.hjiee.core.observer.observeChangedFavoriteState
import com.hjiee.core.observer.observeReviewRegistered
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
                star = detailViewModel.myReview.value?.star.orDefault(0.5f),
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