package com.ddd4.synesthesia.beer.presentation.ui.main.mypage.review.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.Observer
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.databinding.ActivityMyReviewBinding
import com.ddd4.synesthesia.beer.util.ext.observeHandledEvent
import com.ddd4.synesthesia.beer.presentation.base.BaseActivity
import com.hjiee.core.event.entity.ItemClickEntity
import com.ddd4.synesthesia.beer.presentation.commom.adapter.ItemsAdapter
import com.ddd4.synesthesia.beer.presentation.commom.entity.BeerClickEntity
import com.ddd4.synesthesia.beer.presentation.ui.main.mypage.review.viewmodel.MyReviewViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyReviewActivity : BaseActivity<ActivityMyReviewBinding>(R.layout.activity_my_review) {

    private val viewModel by viewModels<MyReviewViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            reviewAdatper = ItemsAdapter(R.layout.item_my_review, BR.review)
            srvReview.setOnRefreshListener {
                viewModel.review()
            }
            includeToolbar.toolbar.setNavigationOnClickListener { finish() }
        }
        initObserver()
    }

    override fun initObserver() {
        super.initObserver()
        viewModel.myReviews.observe(this@MyReviewActivity, Observer {
            it?.let { list ->
                binding.reviewAdatper?.updateItems(list)
                binding.srvReview.isRefreshing = false
            }
        })
        observeHandledEvent(viewModel.event.select) {
            handleSelectEvent(it)
        }
    }

    override fun handleSelectEvent(entity: ItemClickEntity) {
        when (entity) {
            is BeerClickEntity.SelectItem -> {

            }
        }
    }

    companion object {

        fun getIntent(context: Context): Intent {
            return Intent(context, MyReviewActivity::class.java).apply {

            }
        }
    }
}