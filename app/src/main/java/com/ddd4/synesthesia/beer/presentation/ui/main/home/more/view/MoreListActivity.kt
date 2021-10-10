package com.ddd4.synesthesia.beer.presentation.ui.main.home.more.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.databinding.ActivityMoreListBinding
import com.ddd4.synesthesia.beer.ext.observeHandledEvent
import com.ddd4.synesthesia.beer.ext.start
import com.ddd4.synesthesia.beer.presentation.base.BaseActivity
import com.ddd4.synesthesia.beer.presentation.base.entity.ActionEntity
import com.ddd4.synesthesia.beer.presentation.ui.detail.view.BeerDetailActivity
import com.ddd4.synesthesia.beer.presentation.ui.main.home.main.view.HomeStringProvider
import com.ddd4.synesthesia.beer.presentation.ui.main.home.more.entity.MoreListActionEntity
import com.ddd4.synesthesia.beer.presentation.ui.main.home.more.viewmodel.MoreListViewModel
import com.ddd4.synesthesia.beer.util.listener.EndlessRecyclerViewScrollListener
import com.ddd4.synesthesia.beer.util.sort.SortType
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoreListActivity : BaseActivity<ActivityMoreListBinding>(R.layout.activity_more_list) {

    private val viewModel by viewModels<MoreListViewModel>()
    private val adapter by lazy { MoreListAdapter() }
    private val type by lazy { intent.extras?.get(KEY_LIKE_TYPE) }
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
                start(intent = BeerDetailActivity.getIntent(this, entity.id))
            }
        }
    }

    companion object {
        const val REQ_CODE_HOME_LIKE = 20
        const val KEY_LIKE_SORT = "home_like_sort"
        const val KEY_LIKE_TYPE = "home_like_type"
        const val KEY_LIKE_TITLE = "home_like_title"

        @JvmStatic
        fun start(
            context: Context,
            type: HomeStringProvider.Code,
            title: String,
            requestCode: Int = REQ_CODE_HOME_LIKE
        ) {
            when (context) {
                is Activity -> {
                    context.startActivityForResult(
                        Intent(
                            context,
                            MoreListActivity::class.java
                        ).apply {
                            putExtra(KEY_LIKE_TYPE, type)
                            putExtra(KEY_LIKE_TITLE, title)
                        }, requestCode
                    )
                }
                else -> {
                    context.startActivity(Intent(context, MoreListActivity::class.java).apply {
                        putExtra(KEY_LIKE_TYPE, type)
                        putExtra(KEY_LIKE_TITLE, title)
                    })
                }
            }
        }

        @JvmStatic
        fun start(
            fragment: Fragment,
            sort: SortType?,
            type: HomeStringProvider.Code,
            title: String,
            requestCode: Int = REQ_CODE_HOME_LIKE
        ) {
            fragment.startActivityForResult(
                Intent(
                    fragment.context,
                    MoreListActivity::class.java
                ).apply {
                    putExtra(KEY_LIKE_SORT, sort)
                    putExtra(KEY_LIKE_TYPE, type)
                    putExtra(KEY_LIKE_TITLE, title)
                }, requestCode
            )
        }
    }
}