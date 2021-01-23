package com.ddd4.synesthesia.beer.presentation.ui.home.view

import android.os.Bundle
import android.view.View
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.ddd4.synesthesia.beer.BR
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.databinding.FragmentHomeBinding
import com.ddd4.synesthesia.beer.databinding.LayoutHomeContentsBinding
import com.ddd4.synesthesia.beer.ext.observeHandledEvent
import com.ddd4.synesthesia.beer.presentation.base.BaseFragment
import com.ddd4.synesthesia.beer.presentation.base.entity.ItemClickEntity
import com.ddd4.synesthesia.beer.presentation.commom.BeerClickEntity
import com.ddd4.synesthesia.beer.presentation.commom.adapter.LoadingItemsApdater
import com.ddd4.synesthesia.beer.presentation.ui.detail.view.DetailActivity
import com.ddd4.synesthesia.beer.presentation.ui.home.NavigationDirections
import com.ddd4.synesthesia.beer.presentation.ui.home.entity.HomeSelectEntity.*
import com.ddd4.synesthesia.beer.presentation.ui.home.viewmodel.HomeViewModel
import com.ddd4.synesthesia.beer.util.EndlessRecyclerViewScrollListener
import com.ddd4.synesthesia.beer.util.filter.BeerFilter
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val homeViewModel by viewModels<HomeViewModel>()
    private val listAdapter by lazy { LoadingItemsApdater(R.layout.item_home, BR.item) }

    private lateinit var endlessRecyclerViewScrollListener : EndlessRecyclerViewScrollListener

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserver()

        binding.apply {
            vm = homeViewModel
            contents.set()
            adapter = listAdapter
        }

    }

    override fun initObserver() {
        observeHandledEvent(homeViewModel.event.select) {
            handleSelectEvent(it)
        }
    }

    override fun handleSelectEvent(entity: ItemClickEntity) {
        when(entity) {
            is Search -> {
                findNavController().navigate(NavigationDirections.actionToSearch())
            }
            is MyPage -> {
                findNavController().navigate(NavigationDirections.actionToMyPage())
            }
            is Filter -> {
                FilterDialog().run {
                    show(this@HomeFragment.parentFragmentManager, tag)
                }
            }
            is Sort -> {
                val bottom = HomeSortDialog()
                bottom.show(this@HomeFragment.parentFragmentManager, bottom.tag)
            }
            is  BeerClickEntity.SelectItem -> {
                DetailActivity.start(this@HomeFragment,entity.beer.id)
            }
        }
    }

    private fun LayoutHomeContentsBinding.set() {
        endlessRecyclerViewScrollListener = object : EndlessRecyclerViewScrollListener(binding.contents.itemList.layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                homeViewModel.loadMore()
            }
        }

        with(itemList) {
            adapter = listAdapter
            addOnScrollListener(endlessRecyclerViewScrollListener)
        }

        homeViewModel.appConfig.observe(viewLifecycleOwner, Observer {
            preference.setPreference("appConfig", Gson().toJson(it))
        })

        homeViewModel.beerList.observe(viewLifecycleOwner, Observer {
            binding.emptyList.isVisible = it.isNullOrEmpty()
            listAdapter.updateItems(it ?: mutableListOf())
        })

        homeViewModel.beerFilter.observe(viewLifecycleOwner, Observer {
            binding.filterChipGroup.setFilterChips(it)
            endlessRecyclerViewScrollListener.resetState()
            homeViewModel.cursor.value = 0
            homeViewModel.load()
        })
    }

    private fun ChipGroup.setFilterChips(
        filter: BeerFilter
    ) {
        filter.run {
            if (styleFilter.isNullOrEmpty() || aromaFilter.isNullOrEmpty() || countryFilter.isNullOrEmpty()) removeAllViews()

            abvFilter?.let {
                val value = "${it.first}% - ${it.second}%"
                makeChips(this@setFilterChips, mutableListOf(value), "abv")
            }

            styleFilter?.let { makeChips(this@setFilterChips, it, "style") }
            aromaFilter?.let { makeChips(this@setFilterChips, it, "aroma") }
            countryFilter?.let { makeChips(this@setFilterChips, it, "country") }
        }
    }

    private fun makeChips(chipGroup: ChipGroup, items: List<String>, tag: String) {
        items.asSequence().forEach { item ->
            if (chipGroup.children.asIterable().map { view -> view.tag }.contains(item)) return

            val chip: Chip = layoutInflater.inflate(
                R.layout.layout_home_filter_chip,
                binding.filterChipGroup,
                false
            ) as Chip

            chip.apply {
                text = item
                this.tag = item
            }

            chip.setOnCloseIconClickListener {
                chipGroup.removeView(it)
                lifecycleScope.launch(Dispatchers.IO) {
                    homeViewModel.updateFilter(item, tag)
                }
            }

            chipGroup.addView(chip)
        }
    }
}