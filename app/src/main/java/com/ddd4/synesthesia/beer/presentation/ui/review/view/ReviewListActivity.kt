package com.ddd4.synesthesia.beer.presentation.ui.review.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.databinding.ActivityReviewAllBinding
import com.ddd4.synesthesia.beer.presentation.base.BaseActivity
import com.ddd4.synesthesia.beer.presentation.ui.common.review.ReviewListAdapter
import com.ddd4.synesthesia.beer.presentation.ui.review.model.ReviewListActionEntity
import com.ddd4.synesthesia.beer.presentation.ui.review.viewmodel.ReviewListViewModel
import com.ddd4.synesthesia.beer.util.KEY_BEER_ID
import com.ddd4.synesthesia.beer.util.ext.observeHandledEvent
import com.hjiee.core.event.entity.ActionEntity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewListActivity : BaseActivity<ActivityReviewAllBinding>(R.layout.activity_review_all) {

    private val viewModel by viewModels<ReviewListViewModel>()
    private val listAdapter by lazy { ReviewListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBind()
        initObserver()
        viewModel.load()
    }

    override fun initBind() {
        binding.apply {
            viewModel = this@ReviewListActivity.viewModel
            with(rvReview) {
                adapter = listAdapter
            }
            includeToolbar.toolbar.setNavigationOnClickListener { finish() }
        }
    }

    override fun initObserver() {
        observeHandledEvent(viewModel.event.action) {
            handleActionEvent(it)
        }
    }

    override fun handleActionEvent(entity: ActionEntity) {
        when (entity) {
            is ReviewListActionEntity.UpdateUi -> {
                listAdapter.clear()
                listAdapter.addAll(entity.items)
            }
        }
    }

    companion object {

        const val KEY_REVIEW_COUNT = "review_count"

        fun getIntent(
            context: Context,
            beerId: Int,
            reviewCount: Int
        ): Intent {
            return Intent(context, ReviewListActivity::class.java).apply {
                putExtra(KEY_BEER_ID, beerId)
                putExtra(KEY_REVIEW_COUNT, reviewCount)
            }
        }
    }
}