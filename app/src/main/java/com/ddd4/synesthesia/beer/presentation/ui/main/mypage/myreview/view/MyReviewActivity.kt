package com.ddd4.synesthesia.beer.presentation.ui.main.mypage.myreview.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.databinding.ActivityMyReviewBinding
import com.ddd4.synesthesia.beer.presentation.base.BaseActivity
import com.ddd4.synesthesia.beer.presentation.ui.detail.view.BeerDetailActivity
import com.ddd4.synesthesia.beer.presentation.ui.main.mypage.myreview.model.MyReviewActionEntity
import com.ddd4.synesthesia.beer.presentation.ui.main.mypage.myreview.model.MyReviewSelectEntity
import com.ddd4.synesthesia.beer.presentation.ui.main.mypage.myreview.viewmodel.MyReviewViewModel
import com.ddd4.synesthesia.beer.util.ext.observeHandledEvent
import com.ddd4.synesthesia.beer.util.ext.start
import com.hjiee.core.event.entity.ActionEntity
import com.hjiee.core.event.entity.ItemClickEntity
import com.hjiee.core.observer.observeReviewRegistered
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyReviewActivity : BaseActivity<ActivityMyReviewBinding>(R.layout.activity_my_review) {

    private val viewModel by viewModels<MyReviewViewModel>()
    private val adapter by lazy { MyReviewAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            adapter = this@MyReviewActivity.adapter
            srvReview.setOnRefreshListener {
                viewModel.load()
            }
            includeToolbar.toolbar.setNavigationOnClickListener { finish() }
        }
        initObserver()
    }

    override fun initObserver() {
        observeHandledEvent(viewModel.event.select) {
            handleSelectEvent(it)
        }
        observeHandledEvent(viewModel.event.action) {
            handleActionEvent(it)
        }
        observeReviewRegistered {
            viewModel.load()
        }
    }

    override fun handleSelectEvent(entity: ItemClickEntity) {
        when (entity) {
            is MyReviewSelectEntity.SelectMyReview -> {
                start<BeerDetailActivity>(BeerDetailActivity.getIntent(
                    context = this,
                    beerId = entity.item.beer.id
                ))
            }
        }
    }

    override fun handleActionEvent(entity: ActionEntity) {
        when (entity) {
            is MyReviewActionEntity.UpdateUi -> {
                adapter.clear()
                adapter.addAll(entity.items)
                binding.srvReview.isRefreshing = false
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