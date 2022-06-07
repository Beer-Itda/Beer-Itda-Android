package com.hjiee.presentation.ui.main.home.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hjiee.presentation.base.BaseViewModel
import com.hjiee.presentation.commom.entity.BeerClickEntity
import com.hjiee.presentation.ui.common.beer.item.BeerItemViewModelMapper.getBeerItemViewModel
import com.hjiee.presentation.ui.common.filter.AromaProvider
import com.hjiee.presentation.ui.common.filter.StyleProvider
import com.hjiee.presentation.ui.main.home.main.entity.HomeActionEntity
import com.hjiee.presentation.ui.main.home.main.entity.HomeSelectEntity
import com.hjiee.presentation.ui.main.home.main.item.IHomeItemViewModel
import com.hjiee.presentation.ui.main.home.main.item.parent.award.BeerAwardItemViewModel
import com.hjiee.presentation.ui.main.home.main.item.parent.list.BeerListItemViewModel
import com.hjiee.presentation.ui.main.home.main.item.parent.list.BeerListModelMapper.getMapper
import com.hjiee.presentation.ui.main.home.main.view.HomeBeerRecommendType
import com.hjiee.presentation.ui.main.home.main.view.HomeStringProvider
import com.hjiee.presentation.util.ext.EventFlow
import com.hjiee.presentation.util.ext.GlobalEvent
import com.hjiee.presentation.util.ext.safeAsync
import com.hjiee.presentation.util.sort.SortType
import com.hjiee.core.event.entity.ActionEntity
import com.hjiee.core.event.entity.ItemClickEntity
import com.hjiee.core.util.log.L
import com.hjiee.domain.entity.DomainEntity.Beer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: HomeUseCaseGroup,
    private val styleProvider: StyleProvider,
    private val aromaProvider: AromaProvider,
    private val stringProvider: HomeStringProvider
) : BaseViewModel() {

    private val beerItems = mutableListOf<IHomeItemViewModel>()
    private val _beerList = MutableLiveData<List<Beer>?>()
    val beerList: LiveData<List<Beer>?>
        get() = _beerList

    private val _sortType = MutableLiveData<SortType>()
    val sortType: LiveData<SortType> get() = _sortType

    val cursor = MutableLiveData(0)

    private val _isLoadMore = MutableLiveData<Boolean>(false)
    val isLoadMore: LiveData<Boolean> get() = _isLoadMore

    private val _isRefresh = MutableLiveData<Boolean>()
    val isRefresh: LiveData<Boolean> get() = _isRefresh

    private var awardBeer: BeerAwardItemViewModel? = null
    private var styleBeer: BeerListItemViewModel? = null
    private var aromaBeer: BeerListItemViewModel? = null
    private var recommendBeer: BeerListItemViewModel? = null


    init {
        eventListen()
    }

    private fun eventListen() {
        viewModelScope.launch(errorHandler) {
            EventFlow.subscribe<GlobalEvent>().collect { event ->
                when (event) {
                    is GlobalEvent.Favorite -> {
                        styleBeer?.beers?.filter {
                            it.data.id == event.beerId
                        }?.map {
//                            it.data.updateFavorite()
                        }
                    }
                }
            }
        }
    }

    fun refresh() {
        _isRefresh.value = true
        load()
    }

    fun load() {
        if (_isLoadMore.value == false) {
            statusLoading()
        }
        viewModelScope.launch {
            val award = safeAsync { fetchAward() }
            val aroma = safeAsync { fetchAroma() }
            val style = safeAsync { fetchStyle() }
            val recommend = safeAsync { fetchRandomRecommend() }

            awaitAll(award, aroma, style, recommend)

            setData()
            notifyActionEvent(entity = HomeActionEntity.UpdateList(beerItems))
            statusSuccess()
            _isRefresh.value = false
        }
    }

    private fun setData() {
        beerItems.clear()
        awardBeer?.let { beerItems.add(it) }
        styleBeer?.run {
            if (beers.isNotEmpty()) {
                beerItems.add(this)
            }
        }

        aromaBeer?.run {
            if (beers.isNotEmpty()) {
                beerItems.add(this)
            }
        }

        recommendBeer?.run {
            if (beers.isNotEmpty()) {
                beerItems.add(this)
            }
        }
    }

    fun loadSelectedAromaWithBeer() {
        viewModelScope.launch {
            val index = beerItems.indexOf(aromaBeer)
            fetchAroma().let {
                runCatching {
                    beerItems.removeAt(index)
                    it?.let { item ->
                        beerItems.add(index, item)
                    }
                    notifyActionEvent(HomeActionEntity.UpdateList(beerItems))
                }.onFailure {
                    notifyActionEvent(entity = HomeActionEntity.UpdateList(emptyList()))
                    L.e(it)
                }
            }
        }
    }

    fun loadSelectedStyleWithBeer() {
        viewModelScope.launch {
            val index = beerItems.indexOf(styleBeer)
            fetchStyle().let {
                runCatching {
                    beerItems.removeAt(index)
                    it?.let { item ->
                        beerItems.add(index, item)
                    }
                    notifyActionEvent(entity = HomeActionEntity.UpdateList(beerItems))
                }.onFailure {
                    notifyActionEvent(entity = HomeActionEntity.UpdateList(emptyList()))
                    L.e(it)
                }
            }
        }
    }

    private suspend fun fetchAward(): BeerAwardItemViewModel {
        return useCase.awardBeer.execute().getBeerItemViewModel(this@HomeViewModel)
            .let { awardBeerItem ->
                BeerAwardItemViewModel(
                    beer = awardBeerItem
                ).also {
                    awardBeer = it
                }
            }
    }

    private suspend fun fetchAroma(): BeerListItemViewModel? {
        return useCase.getSelectedAromaBeerUseCase.execute()?.data?.getMapper(
            title = stringProvider.getStringRes(HomeBeerRecommendType.AROMA),
            type = HomeBeerRecommendType.AROMA,
            eventNotifier = this@HomeViewModel
        ).also {
            aromaBeer = it
        }
    }

    private suspend fun fetchStyle(): BeerListItemViewModel? {
        return useCase.getSelectedStyleBeerUseCase.execute()?.data?.getMapper(
            title = stringProvider.getStringRes(HomeBeerRecommendType.STYLE),
            type = HomeBeerRecommendType.STYLE,
            eventNotifier = this@HomeViewModel
        ).also {
            styleBeer = it
        }
    }

    private suspend fun fetchRandomRecommend(): BeerListItemViewModel? {
        return useCase.getRandomRecommendBeer.execute()?.data?.getMapper(
            title = stringProvider.getStringRes(HomeBeerRecommendType.RANDOM),
            type = HomeBeerRecommendType.RANDOM,
            eventNotifier = this@HomeViewModel
        ).also {
            recommendBeer = it
        }
    }

    private fun recommendLoadMore(type: HomeBeerRecommendType) {
        viewModelScope.launch {
            when (type) {
                HomeBeerRecommendType.AROMA -> {

                }
                HomeBeerRecommendType.STYLE -> {

                }
                HomeBeerRecommendType.RANDOM -> {

                }
            }
        }
    }

    private fun updateFilter() {
        viewModelScope.launch(errorHandler) {
            aromaProvider.getFlow().collect {
                load()
            }
            styleProvider.getFlow().collect {
                load()
            }
        }
    }

    private fun fetchFavorite(id: Int) {
        viewModelScope.launch {
            useCase.favorite.execute(id)
        }
    }

    override fun handleActionEvent(entity: ActionEntity) {
        when (entity) {
            is HomeActionEntity.LoadMore -> {
                recommendLoadMore(entity.item.type)
            }
        }
    }

    override fun handleSelectEvent(entity: ItemClickEntity) {
        when (entity) {
            is BeerClickEntity.ClickFavorite -> {
                fetchFavorite(entity.beer.id)
//                fetchFavorite(entity.beer)
            }
        }
    }

    fun clickSearch() {
        notifySelectEvent(HomeSelectEntity.Search)
    }

    fun clickMyPage() {
        notifySelectEvent(HomeSelectEntity.MyPage)
    }

    fun clickFilter() {
        notifySelectEvent(HomeSelectEntity.ClickFilter)
    }

    fun clickSort() {
        notifySelectEvent(HomeSelectEntity.Sort)
    }

    private fun removeContainsItem(removeItem: String, items: MutableList<String>?) {
        items ?: return
        if (items.contains(removeItem)) items.remove(removeItem)
    }

    override fun onCleared() {
        super.onCleared()
    }
}