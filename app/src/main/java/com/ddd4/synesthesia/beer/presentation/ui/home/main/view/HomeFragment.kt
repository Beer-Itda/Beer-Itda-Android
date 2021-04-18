package com.ddd4.synesthesia.beer.presentation.ui.home.main.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.databinding.FragmentHomeBinding
import com.ddd4.synesthesia.beer.ext.observeHandledEvent
import com.ddd4.synesthesia.beer.presentation.base.BaseFragment
import com.ddd4.synesthesia.beer.presentation.base.entity.ActionEntity
import com.ddd4.synesthesia.beer.presentation.base.entity.ItemClickEntity
import com.ddd4.synesthesia.beer.presentation.commom.entity.BeerClickEntity
import com.ddd4.synesthesia.beer.presentation.ui.common.sort.view.SortDialog
import com.ddd4.synesthesia.beer.presentation.ui.detail.view.DetailActivity
import com.ddd4.synesthesia.beer.presentation.ui.filter.style.view.StyleActivity
import com.ddd4.synesthesia.beer.presentation.ui.home.NavigationDirections
import com.ddd4.synesthesia.beer.presentation.ui.home.like.view.HomeLikeActivity
import com.ddd4.synesthesia.beer.presentation.ui.home.main.entity.HomeActionEntity
import com.ddd4.synesthesia.beer.presentation.ui.home.main.entity.HomeSelectEntity
import com.ddd4.synesthesia.beer.presentation.ui.home.main.viewmodel.HomeViewModel
import com.ddd4.synesthesia.beer.util.listener.EndlessRecyclerViewScrollListener
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val homeViewModel by viewModels<HomeViewModel>()
    private val homeAdapter by lazy { HomeListAdapter() }

    private lateinit var endlessRecyclerViewScrollListener: EndlessRecyclerViewScrollListener

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserver()
        initView()
        homeViewModel.load()
    }

    override fun initObserver() {
        observeHandledEvent(homeViewModel.event.select) {
            handleSelectEvent(it)
        }
        observeHandledEvent(homeViewModel.event.action) {
            handleActionEvent(it)
        }
    }

    override fun handleActionEvent(entity: ActionEntity) {
        when (entity) {
            is HomeActionEntity.UpdateList -> {
                homeAdapter.clear()
                homeAdapter.addAll(entity.beer)
            }
            is HomeActionEntity.AppConfigSetting -> {
                preference.setPreference("appConfig", Gson().toJson(entity.config))
            }
            is HomeActionEntity.Refresh -> {
                endlessRecyclerViewScrollListener.resetState()
            }
        }
    }

    override fun handleSelectEvent(entity: ItemClickEntity) {
        when (entity) {
            is HomeSelectEntity.Search -> {
                findNavController().navigate(NavigationDirections.actionToSearch())
            }
            is HomeSelectEntity.MyPage -> {
                findNavController().navigate(NavigationDirections.actionToMyPage())
            }
            is HomeSelectEntity.ClickFilter -> {
                StyleActivity.start(requireContext())
            }
            is HomeSelectEntity.Sort -> {
                val bottom = SortDialog()
                bottom.show(this@HomeFragment.parentFragmentManager, bottom.tag)
            }
            is HomeSelectEntity.ClickTitle -> {
                HomeLikeActivity.start(
                    fragment = this@HomeFragment,
                    sort = entity.sort,
                    type = entity.type,
                    title = entity.title
                )
            }
            is BeerClickEntity.SelectItem -> {
                DetailActivity.start(this@HomeFragment, entity.beer.id)
            }
            is BeerClickEntity.SelectBeer -> {
                DetailActivity.start(this@HomeFragment, entity.beer.id)
            }
        }
    }

    private fun initView() {
        binding.apply {
            vm = homeViewModel
            with(rvHome) {
                adapter = homeAdapter
            }
        }

        homeViewModel.run {
//            beerList.observe(viewLifecycleOwner, Observer {
//                binding.emptyList.isVisible = it.isNullOrEmpty()
//                listAdapter.updateItems(it ?: mutableListOf())
//            })

//            beerFilter.observe(viewLifecycleOwner, Observer {
//                binding.filterChipGroup.setFilterChips(it)
//                endlessRecyclerViewScrollListener.resetState()
//                homeViewModel.cursor.value = 0
//            homeViewModel.load()
//                homeViewModel.load2()
//            })
        }
    }


//    private fun ChipGroup.setFilterChips(
//        filter: BeerFilter
//    ) {
//        filter.run {
//            if (styleFilter.isNullOrEmpty() || aromaFilter.isNullOrEmpty() || countryFilter.isNullOrEmpty()) removeAllViews()
//
//            abvFilter?.let {
//                val value = "${it.first}% - ${it.second}%"
//                makeChips(this@setFilterChips, mutableListOf(value), "abv")
//            }
//
//            styleFilter?.let { makeChips(this@setFilterChips, it, "style") }
//            aromaFilter?.let { makeChips(this@setFilterChips, it, "aroma") }
//            countryFilter?.let { makeChips(this@setFilterChips, it, "country") }
//        }
//    }

//    private fun makeChips(chipGroup: ChipGroup, items: List<String>, tag: String) {
//        items.asSequence().forEach { item ->
//            if (chipGroup.children.asIterable().map { view -> view.tag }.contains(item)) return
//
//            val chip: Chip = layoutInflater.inflate(
//                R.layout.layout_home_filter_chip,
//                binding.filterChipGroup,
//                false
//            ) as Chip
//
//            chip.apply {
//                text = item
//                this.tag = item
//            }
//
//            chip.setOnCloseIconClickListener {
//                chipGroup.removeView(it)
//                lifecycleScope.launch(Dispatchers.IO) {
//                    homeViewModel.updateFilter(item, tag)
//                }
//            }
//
//            chipGroup.addView(chip)
//        }
//    }
}