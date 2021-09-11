package com.ddd4.synesthesia.beer.presentation.ui.main.home.main.view

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
import com.ddd4.synesthesia.beer.presentation.ui.filter.aroma.view.AromaActivity
import com.ddd4.synesthesia.beer.presentation.ui.filter.style.view.StyleActivity
import com.ddd4.synesthesia.beer.presentation.ui.main.home.main.entity.HomeActionEntity
import com.ddd4.synesthesia.beer.presentation.ui.main.home.main.entity.HomeSelectEntity
import com.ddd4.synesthesia.beer.presentation.ui.main.home.main.viewmodel.HomeViewModel
import com.ddd4.synesthesia.beer.presentation.ui.main.home.more.view.MoreListActivity
import com.ddd4.synesthesia.beer.util.listener.EndlessRecyclerViewScrollListener
import com.google.gson.Gson
import com.hyden.ext.start
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
                findNavController().navigate(R.id.action_to_search)
            }
            is HomeSelectEntity.MyPage -> {
                findNavController().navigate(R.id.action_to_mypage)
            }
            is HomeSelectEntity.ClickFilter -> {
                context?.let {
                    start(intent = AromaActivity.getIntent(it))
                }
            }
            is HomeSelectEntity.Sort -> {
                val bottom = SortDialog()
                bottom.show(this@HomeFragment.parentFragmentManager, bottom.tag)
            }
            is HomeSelectEntity.ClickTitle -> {
                MoreListActivity.start(
                    fragment = this@HomeFragment,
                    sort = entity.sort,
                    type = entity.type,
                    title = entity.title
                )
            }
            is BeerClickEntity.SelectItem -> {
                moveToDetail(entity.beer.id)
            }
            is BeerClickEntity.SelectBeer -> {
                moveToDetail(entity.beer.id)
            }
        }
    }

    private fun moveToDetail(beerId: Int) {
        context?.let {
            start(intent = DetailActivity.getIntent(it, beerId))
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