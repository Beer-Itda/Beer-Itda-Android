package com.ddd4.synesthesia.beer.presentation.ui.detail.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
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
        observeChangedFavoriteState {
            detailViewModel.load()
        }
        observeReviewRegistered {
            //TODO review data reload
        }
    }

    override fun handleActionEvent(entity: ActionEntity) {
        when (entity) {
            is BeerDetailActionEntity.UpdateUi -> {
                detailAdapter.clear()
                detailAdapter.addAll(entity.items)
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
        }
    }

    private fun reviewWriteBottomSheetDialog() {
        ReviewWriteBottomSheetDialogFragment().apply {
            arguments = getBundle(
                beerId = beerId
//                reviewContent = detailViewModel.item.value?.myReview?.content,
//                reviewRatio = detailViewModel.item.value?.myReview?.ratio.orDefault(0.5f),
//                isFirstWrite = detailViewModel.item.value?.myReview?.reviewId.orZero() == 0
            )
            setCallbackListener {
                context?.showToast(getString(R.string.success_registered_review))
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