package com.ddd4.synesthesia.beer.presentation.ui.like.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.databinding.ActivityHomeLikeBinding
import com.ddd4.synesthesia.beer.ext.observeHandledEvent
import com.ddd4.synesthesia.beer.presentation.base.BaseActivity
import com.ddd4.synesthesia.beer.presentation.base.entity.ActionEntity
import com.ddd4.synesthesia.beer.presentation.base.entity.ItemClickEntity
import com.ddd4.synesthesia.beer.presentation.commom.entity.BeerClickEntity
import com.ddd4.synesthesia.beer.presentation.ui.common.filter.BeerFilter
import com.ddd4.synesthesia.beer.presentation.ui.detail.view.DetailActivity
import com.ddd4.synesthesia.beer.presentation.ui.home.view.HomeStringProvider
import com.ddd4.synesthesia.beer.presentation.ui.like.entity.HomeLikeActionEntity
import com.ddd4.synesthesia.beer.presentation.ui.like.viewmodel.HomeLikeViewModel
import com.ddd4.synesthesia.beer.util.EndlessRecyclerViewScrollListener
import com.ddd4.synesthesia.beer.util.sort.SortType
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeLikeActivity : BaseActivity<ActivityHomeLikeBinding>(R.layout.activity_home_like) {

    private val viewModel by viewModels<HomeLikeViewModel>()
    private val adapter by lazy { HomeLikeListAdapter() }
    private val type by lazy { intent.extras?.get(KEY_LIKE_TYPE) }
    private val title by lazy { intent.extras?.get(KEY_LIKE_TITLE) }
    private val filter by lazy { intent.extras?.get(KEY_LIKE_FILTER) }
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
                adapter = this@HomeLikeActivity.adapter
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
            is HomeLikeActionEntity.UpdateList -> {
                adapter.clear()
                adapter.addAll(entity.data.orEmpty())
            }
            is HomeLikeActionEntity.Refresh -> {
                endlessRecyclerViewScrollListener.resetState()
            }
            is HomeLikeActionEntity.MoveToDetail -> {
                DetailActivity.start(this@HomeLikeActivity, entity.id)
            }
        }
    }

    companion object {
        const val REQ_CODE_HOME_LIKE = 20
        const val KEY_LIKE_SORT = "home_like_sort"
        const val KEY_LIKE_TYPE = "home_like_type"
        const val KEY_LIKE_TITLE = "home_like_title"
        const val KEY_LIKE_FILTER = "home_like_filter"

        @JvmStatic
        fun start(
            context: Context,
            type: HomeStringProvider.Code,
            title: String,
            filter: BeerFilter,
            requestCode: Int = REQ_CODE_HOME_LIKE
        ) {
            when (context) {
                is Activity -> {
                    context.startActivityForResult(
                        Intent(
                            context,
                            HomeLikeActivity::class.java
                        ).apply {
                            putExtra(KEY_LIKE_TYPE, type)
                            putExtra(KEY_LIKE_TITLE, title)
                            putExtra(KEY_LIKE_FILTER, filter)
                        }, requestCode
                    )
                }
                else -> {
                    context.startActivity(Intent(context, HomeLikeActivity::class.java).apply {
                        putExtra(KEY_LIKE_TYPE, type)
                        putExtra(KEY_LIKE_TITLE, title)
                        putExtra(KEY_LIKE_FILTER, filter)
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
            filter: BeerFilter,
            requestCode: Int = REQ_CODE_HOME_LIKE
        ) {
            fragment.startActivityForResult(
                Intent(
                    fragment.context,
                    HomeLikeActivity::class.java
                ).apply {
                    putExtra(KEY_LIKE_SORT, sort)
                    putExtra(KEY_LIKE_TYPE, type)
                    putExtra(KEY_LIKE_TITLE, title)
                    putExtra(KEY_LIKE_FILTER, filter)
                }, requestCode
            )
        }
    }
}