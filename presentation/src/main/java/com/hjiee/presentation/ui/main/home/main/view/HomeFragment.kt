package com.hjiee.presentation.ui.main.home.main.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.hjiee.core.event.entity.ActionEntity
import com.hjiee.core.event.entity.ItemClickEntity
import com.hjiee.core.observer.observeChangeSelectedConfiguration
import com.hjiee.core.observer.observeChangedFavoriteState
import com.hjiee.core.util.log.L
import com.hjiee.presentation.R
import com.hjiee.presentation.base.BaseFragment
import com.hjiee.presentation.commom.entity.BeerClickEntity
import com.hjiee.presentation.databinding.FragmentHomeBinding
import com.hjiee.presentation.ui.common.sort.view.SortDialog
import com.hjiee.presentation.ui.detail.view.BeerDetailActivity
import com.hjiee.presentation.ui.filter.aroma.view.AromaActivity
import com.hjiee.presentation.ui.main.home.main.entity.HomeActionEntity
import com.hjiee.presentation.ui.main.home.main.entity.HomeSelectEntity
import com.hjiee.presentation.ui.main.home.main.viewmodel.HomeViewModel
import com.hjiee.presentation.ui.main.home.more.view.MoreListActivity
import com.hjiee.presentation.util.ext.observeHandledEvent
import com.hjiee.presentation.util.ext.start
import com.hjiee.presentation.util.listener.EndlessRecyclerViewScrollListener
import dagger.hilt.android.AndroidEntryPoint
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

        firstLoaded {
            homeViewModel.load()
        }
    }

    override fun initObserver() {
        observeHandledEvent(homeViewModel.event.select) {
            handleSelectEvent(it)
        }
        observeHandledEvent(homeViewModel.event.action) {
            handleActionEvent(it)
        }
        observeChangeSelectedConfiguration {
            homeViewModel.refresh()
        }
        observeChangedFavoriteState {
            homeViewModel.refresh()
        }
    }

    override fun handleActionEvent(entity: ActionEntity) {
        when (entity) {
            is HomeActionEntity.UpdateList -> {
                homeAdapter.clear()
                homeAdapter.addAll(entity.beer)
            }
//            is HomeActionEntity.AppConfigSetting -> {
//                preference.setPreference("appConfig", Gson().toJson(entity.config))
//            }
            is HomeActionEntity.Refresh -> {
                endlessRecyclerViewScrollListener.resetState()
            }
        }
    }

    override fun handleSelectEvent(entity: ItemClickEntity) {
        when (entity) {
            is HomeSelectEntity.Search -> {
                findNavController().navigate(R.id.action_to_search)
            }
            is HomeSelectEntity.MyPage -> {
                findNavController().navigate(R.id.action_to_mypage)
            }
            is HomeSelectEntity.ClickFilter -> {
                context?.let {
                    start<AromaActivity>()
                }
            }
            is HomeSelectEntity.Sort -> {
                val bottom = SortDialog()
                bottom.show(this@HomeFragment.parentFragmentManager, bottom.tag)
            }
            is HomeSelectEntity.ClickTitle -> {
                runCatching {
                    start<MoreListActivity>(
                        intent = MoreListActivity.getIntent(
                            context = requireContext(),
                            type = entity.type,
                            title = entity.title
                        )
                    )
                }.onFailure {
                    L.e(it)
                }
            }
            is BeerClickEntity.ClickItem -> {
                moveToDetail(entity.beer.id)
            }
            is BeerClickEntity.ClickBeer -> {
                moveToDetail(entity.beer.id)
            }
        }
    }

    private fun moveToDetail(beerId: Int) {
        context?.let {
            start<BeerDetailActivity>(intent = BeerDetailActivity.getIntent(it, beerId))
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