package com.ddd4.synesthesia.beer.presentation.ui.main.search.view

import android.os.Bundle
import android.view.View
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.databinding.FragmentSearchBinding
import com.ddd4.synesthesia.beer.util.ext.observeHandledEvent
import com.ddd4.synesthesia.beer.util.ext.showKeyboard
import com.ddd4.synesthesia.beer.presentation.base.BaseFragment
import com.hjiee.core.event.entity.ItemClickEntity
import com.ddd4.synesthesia.beer.presentation.commom.entity.BeerClickEntity
import com.ddd4.synesthesia.beer.presentation.commom.adapter.LoadingItemsApdater
import com.ddd4.synesthesia.beer.presentation.ui.detail.view.BeerDetailActivity
import com.ddd4.synesthesia.beer.presentation.ui.main.search.viewmodel.SearchViewModel
import com.ddd4.synesthesia.beer.util.listener.EndlessRecyclerViewScrollListener
import com.ddd4.synesthesia.beer.util.ext.start
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {

    private val viewModel by viewModels<SearchViewModel>()

    private val titleAdapter by lazy {
        LoadingItemsApdater(
            R.layout.item_auto_completation,
            BR.item
        )
    }
    private val imageAdapter by lazy { LoadingItemsApdater(R.layout.item_home, BR.beer) }
    private lateinit var titleEndlessScroll: EndlessRecyclerViewScrollListener
    private lateinit var imageEndlessScroll: EndlessRecyclerViewScrollListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBind()
        initObserver()
    }

    override fun initBind() {
        super.initBind()
        titleEndlessScroll =
            object : EndlessRecyclerViewScrollListener(binding.rvOnlyTitle.layoutManager) {
                override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                    viewModel.loadMore()
                }
            }
        imageEndlessScroll =
            object : EndlessRecyclerViewScrollListener(binding.rvWithImage.layoutManager) {
                override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                    viewModel.loadMore()
                }
            }

        binding.apply {
            vm = viewModel
            titleAdapter = this@SearchFragment.titleAdapter
            imageAdapter = this@SearchFragment.imageAdapter
            ibSearch.setOnClickListener { viewModel.isTemplateVisible.set(true) }
            ibClear.setOnClickListener {
                viewModel.clearText()
                context?.showKeyboard(etSearch)
            }
            srvSearchImage.setOnRefreshListener { viewModel.refresh() }
            srvSearchTitle.setOnRefreshListener { viewModel.refresh() }
            rvOnlyTitle.addOnScrollListener(titleEndlessScroll)
            rvWithImage.addOnScrollListener(imageEndlessScroll)
        }
    }

    override fun initObserver() {
        with(viewModel) {
            beerList.observe(viewLifecycleOwner, Observer {
                imageAdapter.updateItems(it.orEmpty())
                titleAdapter.updateItems(it.orEmpty())
                binding.srvSearchTitle.isRefreshing = false
                binding.srvSearchImage.isRefreshing = false
            })
        }
        observeHandledEvent(viewModel.event.select) {
            handleSelectEvent(it)
        }
    }

    override fun handleSelectEvent(entity: ItemClickEntity) {
        when (entity) {
            is BeerClickEntity.SelectItem -> {
                context?.let {
                    start<BeerDetailActivity>(intent = BeerDetailActivity.getIntent(it, entity.beer.id))
                }
            }
        }
    }
}