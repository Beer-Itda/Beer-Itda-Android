package com.hjiee.presentation.ui.main.mypage.favorite.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.hjiee.presentation.R
import com.hjiee.presentation.databinding.ActivityMyFavoriteBinding
import com.hjiee.presentation.base.BaseActivity
import com.hjiee.presentation.commom.entity.BeerClickEntity
import com.hjiee.presentation.ui.detail.view.BeerDetailActivity
import com.hjiee.presentation.ui.main.mypage.favorite.model.MyFavoriteActionEntity
import com.hjiee.presentation.ui.main.mypage.favorite.viewmodel.MyFavoriteViewModel
import com.hjiee.presentation.util.ext.observeHandledEvent
import com.hjiee.presentation.util.ext.start
import com.hjiee.presentation.util.listener.EndlessRecyclerViewScrollListener
import com.hjiee.core.event.entity.ActionEntity
import com.hjiee.core.event.entity.ItemClickEntity
import dagger.hilt.android.AndroidEntryPoint

/**
 * 내가 찜한 맥주 리스트
 */
@AndroidEntryPoint
class MyFavoriteActivity : BaseActivity<ActivityMyFavoriteBinding>(R.layout.activity_my_favorite) {

    private val viewModel by viewModels<MyFavoriteViewModel>()
    private val adapter by lazy { MyFavoriteAdapter() }
    private val endlessRecyclerViewScrollListener: EndlessRecyclerViewScrollListener by lazy {
        object : EndlessRecyclerViewScrollListener(binding.rvMyFavorite.layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                viewModel.loadMore()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBind()
        initObserver()
    }

    override fun initBind() {
        binding.apply {
            vm = viewModel
            includeToolbar.toolbar.setNavigationOnClickListener { finish() }
            with(rvMyFavorite) {
                adapter = this@MyFavoriteActivity.adapter
                addOnScrollListener(endlessRecyclerViewScrollListener)
            }
        }
    }

    override fun initObserver() {
        observeHandledEvent(viewModel.event.select) {
            handleSelectEvent(it)
        }
        observeHandledEvent(viewModel.event.action) {
            handleActionEvent(it)
        }
    }

    override fun handleActionEvent(entity: ActionEntity) {
        when (entity) {
            is MyFavoriteActionEntity.UpdateUi -> {
                adapter.clear()
                adapter.addAll(entity.viewModel)
            }
            is MyFavoriteActionEntity.Refresh -> {
                endlessRecyclerViewScrollListener.resetState()
            }
        }
    }

    override fun handleSelectEvent(entity: ItemClickEntity) {
        when (entity) {
            is BeerClickEntity.ClickBeer -> {
                start<BeerDetailActivity>(
                    intent = BeerDetailActivity.getIntent(
                        this,
                        entity.beer.id
                    )
                )
            }
        }
    }

    companion object {

        fun getIntent(context: Context): Intent {
            return Intent(context, MyFavoriteActivity::class.java).apply {

            }
        }
    }
}