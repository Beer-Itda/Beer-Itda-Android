package com.hjiee.presentation.ui.main.mypage.myreview.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.hjiee.presentation.R
import com.hjiee.presentation.databinding.ActivityMyReviewBinding
import com.hjiee.presentation.base.BaseActivity
import com.hjiee.presentation.ui.detail.view.BeerDetailActivity
import com.hjiee.presentation.ui.main.mypage.myreview.model.MyReviewActionEntity
import com.hjiee.presentation.ui.main.mypage.myreview.model.MyReviewSelectEntity
import com.hjiee.presentation.ui.main.mypage.myreview.viewmodel.MyReviewViewModel
import com.hjiee.presentation.util.ext.observeHandledEvent
import com.hjiee.presentation.util.ext.start
import com.hjiee.presentation.util.listener.EndlessRecyclerViewScrollListener
import com.hjiee.core.event.entity.ActionEntity
import com.hjiee.core.event.entity.ItemClickEntity
import com.hjiee.core.observer.observeReviewRegistered
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyReviewActivity : BaseActivity<ActivityMyReviewBinding>(R.layout.activity_my_review) {

    private val viewModel by viewModels<MyReviewViewModel>()
    private val adapter by lazy { MyReviewAdapter() }
    private val endlessRecyclerViewScrollListener: EndlessRecyclerViewScrollListener by lazy {
        object : EndlessRecyclerViewScrollListener(binding.rvMyReview.layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                viewModel.loadMore()
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            with(rvMyReview) {
                adapter = this@MyReviewActivity.adapter
                addOnScrollListener(endlessRecyclerViewScrollListener)
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