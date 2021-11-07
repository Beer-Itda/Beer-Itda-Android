package com.ddd4.synesthesia.beer.presentation.ui.main.home.main.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.ddd4.synesthesia.beer.data.model.AppConfig
import com.ddd4.synesthesia.beer.data.model.Beer
import com.ddd4.synesthesia.beer.domain.repository.BeerRepository
import com.ddd4.synesthesia.beer.ext.EventFlow
import com.ddd4.synesthesia.beer.ext.GlobalEvent
import com.ddd4.synesthesia.beer.presentation.base.BaseViewModel
import com.ddd4.synesthesia.beer.presentation.base.entity.ActionEntity
import com.ddd4.synesthesia.beer.presentation.base.entity.ItemClickEntity
import com.ddd4.synesthesia.beer.presentation.commom.entity.BeerClickEntity
import com.ddd4.synesthesia.beer.presentation.ui.common.beer.item.BeerItemViewModel
import com.ddd4.synesthesia.beer.presentation.ui.common.filter.AromaProvider
import com.ddd4.synesthesia.beer.presentation.ui.common.filter.StyleProvider
import com.ddd4.synesthesia.beer.presentation.ui.main.home.main.entity.HomeActionEntity
import com.ddd4.synesthesia.beer.presentation.ui.main.home.main.entity.HomeSelectEntity
import com.ddd4.synesthesia.beer.presentation.ui.main.home.main.item.IHomeItemViewModel
import com.ddd4.synesthesia.beer.presentation.ui.main.home.main.item.parent.award.BeerAwardItemViewModel
import com.ddd4.synesthesia.beer.presentation.ui.main.home.main.item.parent.award.BeerAwardModelMapper
import com.ddd4.synesthesia.beer.presentation.ui.main.home.main.item.parent.list.BeerListItemViewModel
import com.ddd4.synesthesia.beer.presentation.ui.main.home.main.item.parent.list.BeerListModelMapper.getMapper
import com.ddd4.synesthesia.beer.presentation.ui.main.home.main.view.HomeStringProvider
import com.ddd4.synesthesia.beer.util.sort.SortType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.random.Random

@ExperimentalCoroutinesApi
class HomeViewModel @ViewModelInject constructor(
    private val beerRepository: BeerRepository,
    private val styleProvider: StyleProvider,
    private val aromaProvider: AromaProvider,
    private val stringProvider: HomeStringProvider,
    @Assisted val savedState: SavedStateHandle
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

    private val _appConfig = MutableLiveData<AppConfig>()
    val appConfig: LiveData<AppConfig> get() = _appConfig

    private val _isRefresh = MutableLiveData<Boolean>()
    val isRefresh: LiveData<Boolean> get() = _isRefresh

    private var awardBeer: BeerAwardItemViewModel? = null
    private var styleBeer: BeerListItemViewModel? = null
    private var aromaBeer: BeerListItemViewModel? = null
    private var recommendBeer :BeerListItemViewModel? = null


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
                            it.data.updateFavorite()
                        }
                    }
                }
            }
        }
    }

    private fun loadAppConfig() {
        viewModelScope.launch(errorHandler) {
            _appConfig.value = beerRepository.getAppConfig().result
            notifyActionEvent(HomeActionEntity.AppConfigSetting(_appConfig.value))
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
        viewModelScope.launch(errorHandler) {
            awardBeer = fetchAward()
            styleBeer = fetchStyle()
            aromaBeer = fetchAroma()
            recommendBeer = fetchRecommend()

            beerItems.clear()
            awardBeer?.let {
                beerItems.add(it)
            }
            styleBeer?.run {
                if(beers.isNotEmpty()) {
                    beerItems.add(this)
                }
            }

            aromaBeer?.run {
                if(beers.isNotEmpty()) {
                    beerItems.add(this)
                }
            }

            recommendBeer?.run {
                if(beers.isNotEmpty()) {
                    beerItems.add(this)
                }
            }

            notifyActionEvent(entity = HomeActionEntity.UpdateList(beerItems))
            statusSuccess()
            _isRefresh.value = false
        }
    }

    private suspend fun fetchAward(): BeerAwardItemViewModel? {
        val awardResponse = beerRepository.getPopularBeer()?.beers
            .orEmpty()
            .getMapper(
                sortType = _sortType.value,
                type = HomeStringProvider.Code.STYLE,
                title = stringProvider.getStringRes(HomeStringProvider.Code.STYLE),
                eventNotifier = this@HomeViewModel
            )

        val randomIndex = Random.nextInt(awardResponse.beers.size)
        return awardResponse.beers[randomIndex]
            .let {
                BeerAwardModelMapper.getMapper(
                    it.data.apply {
                        eventNotifier = this@HomeViewModel
                    })
            }
    }

    private suspend fun fetchStyle(): BeerListItemViewModel {
        return beerRepository.getBeerList(
            sortType = _sortType.value?.value,
            style = styleProvider.getNames(),
            cursor = cursor.value
        )?.beers.orEmpty()
            .getMapper(
                sortType = _sortType.value,
                type = HomeStringProvider.Code.STYLE,
                style = styleProvider.data,
                title = stringProvider.getStringRes(HomeStringProvider.Code.STYLE),
                eventNotifier = this@HomeViewModel
            )
    }

    private suspend fun fetchAroma(): BeerListItemViewModel {
        return beerRepository.getBeerList(
            sortType = _sortType.value?.value,
            aroma = aromaProvider.getNames(),
            cursor = cursor.value
        )?.beers.orEmpty().getMapper(
            sortType = _sortType.value,
            type = HomeStringProvider.Code.AROMA,
            aroma = aromaProvider.data,
            title = stringProvider.getStringRes(HomeStringProvider.Code.AROMA),
            eventNotifier = this@HomeViewModel
        )
    }

    private suspend fun fetchRecommend(): BeerListItemViewModel {
        return beerRepository.getBeerList(
            sortType = _sortType.value?.value,
            cursor = cursor.value
        )?.beers.orEmpty().getMapper(
            sortType = _sortType.value,
            type = HomeStringProvider.Code.RANDOM,
            title = stringProvider.getStringRes(HomeStringProvider.Code.RANDOM),
            eventNotifier = this@HomeViewModel
        )
    }

    fun loadMore() {
        viewModelScope.launch(errorHandler) {
            cursor.value?.let {
                if (_isLoadMore.value == false) {
                    _beerList.value =
                        _beerList.value?.toMutableList()?.apply { addAll(listOf(Beer(id = -1))) }
                    _isLoadMore.value = true
                    load()
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

    private fun fetchFavorite(beer: BeerItemViewModel) {
        viewModelScope.launch(errorHandler) {
            beer.updateFavorite()
            beerRepository.postFavorite(beer.id, beer.isFavorite.get())
        }
    }

    override fun handleActionEvent(entity: ActionEntity) {
        when (entity) {
            is HomeActionEntity.LoadMore -> {
                loadMore()
            }
        }
    }

    override fun handleSelectEvent(entity: ItemClickEntity) {
        when (entity) {
            is BeerClickEntity.SelectFavorite2 -> {
                fetchFavorite(entity.beer)
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