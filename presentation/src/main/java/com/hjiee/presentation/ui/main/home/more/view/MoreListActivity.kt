package com.hjiee.presentation.ui.main.home.more.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.hjiee.presentation.R
import com.hjiee.presentation.databinding.ActivityMoreListBinding
import com.hjiee.presentation.base.BaseActivity
import com.hjiee.presentation.ui.detail.view.BeerDetailActivity
import com.hjiee.presentation.ui.main.home.main.view.HomeBeerRecommendType
import com.hjiee.presentation.ui.main.home.more.entity.MoreListActionEntity
import com.hjiee.presentation.ui.main.home.more.viewmodel.MoreListViewModel
import com.hjiee.presentation.util.ext.observeHandledEvent
import com.hjiee.presentation.util.ext.start
import com.hjiee.presentation.util.listener.EndlessRecyclerViewScrollListener
import com.hjiee.core.event.entity.ActionEntity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoreListActivity : BaseActivity<ActivityMoreListBinding>(R.layout.activity_more_list) {

    private val viewModel by viewModels<MoreListViewModel>()
    private val adapter by lazy { MoreListAdapter() }
    private val title by lazy { intent.extras?.get(KEY_LIKE_TITLE) }
    private val endlessRecyclerViewScrollListener: EndlessRecyclerViewScrollListener by lazy {
        object : EndlessRecyclerViewScrollListener(binding.rvHomeLike.layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                viewModel.loadMore()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBind()
        initObserver()

        viewModel.load()
    }

    override fun initBind() {
        binding.apply {
            vm = viewModel
            includeToolbar.toolbar.setNavigationOnClickListener { finish() }
            with(rvHomeLike) {
                adapter = this@MoreListActivity.adapter
                addOnScrollListener(endlessRecyclerViewScrollListener)
            }
        }
    }

    override fun initObserver() {
        observeHandledEvent(viewModel.event.action) {
            handleActionEvent(it)
        }
    }

    override fun handleActionEvent(entity: ActionEntity) {
        when (entity) {
            is MoreListActionEntity.UpdateList -> {
                adapter.clear()
                adapter.addAll(entity.data.orEmpty())
            }
            is MoreListActionEntity.Refresh -> {
                endlessRecyclerViewScrollListener.resetState()
            }
            is MoreListActionEntity.MoveToDetail -> {
                start<BeerDetailActivity>(intent = BeerDetailActivity.getIntent(this, entity.id))
            }
        }
    }

    companion object {
        const val REQ_CODE_HOME_LIKE = 20
        const val KEY_LIKE_SORT = "home_like_sort"
        const val KEY_LIKE_TYPE = "home_like_type"
        const val KEY_LIKE_TITLE = "home_like_title"

        fun getIntent(
            context: Context,
            type: HomeBeerRecommendType,
            title: String
        ): Intent {
            return Intent(context, MoreListActivity::class.java).apply {
                putExtra(KEY_LIKE_TYPE, type)
                putExtra(KEY_LIKE_TITLE, title)
            }
        }

    }
}