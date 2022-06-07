package com.hjiee.presentation.ui.review.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.hjiee.presentation.R
import com.hjiee.presentation.databinding.ActivityReviewAllBinding
import com.hjiee.presentation.base.BaseActivity
import com.hjiee.presentation.ui.common.review.ReviewListAdapter
import com.hjiee.presentation.ui.review.model.ReviewListActionEntity
import com.hjiee.presentation.ui.review.viewmodel.ReviewListViewModel
import com.hjiee.presentation.util.KEY_BEER_ID
import com.hjiee.presentation.util.ext.observeHandledEvent
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