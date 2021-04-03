package com.ddd4.synesthesia.beer.presentation.ui.home.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.ddd4.synesthesia.beer.data.model.AppConfig
import com.ddd4.synesthesia.beer.data.model.Beer
import com.ddd4.synesthesia.beer.domain.repository.BeerRepository
import com.ddd4.synesthesia.beer.ext.ChannelType
import com.ddd4.synesthesia.beer.ext.CoroutinesEvent
import com.ddd4.synesthesia.beer.presentation.base.BaseViewModel
import com.ddd4.synesthesia.beer.presentation.base.entity.ActionEntity
import com.ddd4.synesthesia.beer.presentation.base.entity.ItemClickEntity
import com.ddd4.synesthesia.beer.presentation.commom.entity.BeerClickEntity
import com.ddd4.synesthesia.beer.presentation.ui.common.beer.item.BeerItemViewModel
import com.ddd4.synesthesia.beer.presentation.ui.home.entity.HomeActionEntity
import com.ddd4.synesthesia.beer.presentation.ui.home.entity.HomeSelectEntity
import com.ddd4.synesthesia.beer.presentation.ui.common.filter.BeerFilter
import com.ddd4.synesthesia.beer.presentation.ui.common.filter.FilterSetting
import com.ddd4.synesthesia.beer.presentation.ui.common.filter.orEmpty
import com.ddd4.synesthesia.beer.presentation.ui.home.item.IHomeItemViewModel
import com.ddd4.synesthesia.beer.presentation.ui.home.item.child.HomeBeerChildItemViewModel
import com.ddd4.synesthesia.beer.presentation.ui.home.item.parent.award.BeerAwardItemViewModel
import com.ddd4.synesthesia.beer.presentation.ui.home.item.parent.award.BeerAwardModelMapper
import com.ddd4.synesthesia.beer.presentation.ui.home.item.parent.list.BeerListItemViewModel
import com.ddd4.synesthesia.beer.presentation.ui.home.item.parent.list.BeerListModelMapper.getMapper
import com.ddd4.synesthesia.beer.presentation.ui.home.view.HomeStringProvider
import com.ddd4.synesthesia.beer.util.sort.SortSetting
import com.ddd4.synesthesia.beer.util.sort.SortType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.random.Random

@ExperimentalCoroutinesApi
class HomeViewModel @ViewModelInject constructor(
    private val beerRepository: BeerRepository,
    private val sortSetting: SortSetting,
    private val filterSetting: FilterSetting,
    private val stringProvider: HomeStringProvider,
    @Assisted val savedState: SavedStateHandle
) : BaseViewModel() {

    private val beers = mutableListOf<IHomeItemViewModel>()
    private val _beerList = MutableLiveData<List<Beer>?>()
    val beerList: LiveData<List<Beer>?>
        get() = _beerList

    private val _sortType = MutableLiveData<SortType>()
    val sortType: LiveData<SortType> get() = _sortType

    private val _beerFilter = MutableLiveData<BeerFilter>()
    val beerFilter: LiveData<BeerFilter> get() = _beerFilter

    val cursor = MutableLiveData(0)

    private val _isLoadMore = MutableLiveData<Boolean>(false)
    val isLoadMore: LiveData<Boolean> get() = _isLoadMore

    private val _appConfig = MutableLiveData<AppConfig>()
    val appConfig: LiveData<AppConfig> get() = _appConfig

    private val _isRefresh = MutableLiveData<Boolean>()
    val isRefresh: LiveData<Boolean> get() = _isRefresh

    val coroutineEvent = CoroutinesEvent.listen(ChannelType.Favorite::class.java)

    init {
        loadAppConfig()
        filterSort()
        eventListen()
    }

    private fun eventListen() {
        viewModelScope.launch {
            coroutineEvent.consumeEach { favorite ->
                beerList.value?.filter {
                    it.id == favorite.beer?.id
                }?.map {
                    it.updateFavorite()
                    it
                }
            }
        }
    }

    private fun loadAppConfig() {
        viewModelScope.launch {
            _appConfig.value = beerRepository.getAppConfig().result
            notifyActionEvent(HomeActionEntity.AppConfigSetting(_appConfig.value))
        }
    }

    fun loadMore() {
        viewModelScope.launch {
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

    fun load() {
        if (_isLoadMore.value == false) {
            statusLoading()
        }
        viewModelScope.launch {
            val response =
                beerRepository.getBeerList(_sortType.value?.value, _beerFilter.value, cursor.value)

            if (response?.beers.isNullOrEmpty() && cursor.value == 0) {
                _beerList.value = response?.beers?.map { beer ->
                    beer.setFavorite()
                    beer.eventNotifier = this@HomeViewModel
                    beer
                }
            }

            cursor.value = response?.nextCursor
            if (_isLoadMore.value == true) {
                _beerList.value = (_beerList.value?.toMutableList()
                    ?.apply { _beerList.value?.let { removeAt(it.size - 1) } })
                _beerList.value = (_beerList.value?.let { beers ->
                    beers.toMutableList().apply {
                        response?.beers?.let { data ->
                            val beers = data.map { beer ->
                                beer.setFavorite()
                                beer.eventNotifier = this@HomeViewModel
                                beer
                            }
                            addAll(beers)
                        }
                    }
                })
            } else {
                response?.beers?.let {
                    _beerList.value = it.map { beer ->
                        beer.setFavorite()
                        beer.eventNotifier = this@HomeViewModel
                        beer
                    }
                }
            }
            _isLoadMore.value = false
            statusSuccess()
        }
    }

    fun refresh() {
        load2()
        _isRefresh.value = true
    }

    fun load2() {
        if (_isLoadMore.value == false) {
            statusLoading()
        }
        viewModelScope.launch {
            val awardBeer = fetchAward()
            val styleBeer = fetchStyle()
            val aromaBeer = fetchAroma()
            val recommandBeer = fetchRecommand()

            beers.clear()
            awardBeer?.let {
                beers.add(it)
            }
            if (!styleBeer.beers.isNullOrEmpty()) {
                beers.add(styleBeer)
            }
            if (!aromaBeer.beers.isNullOrEmpty()) {
                beers.add(aromaBeer)
            }
            if (!recommandBeer.beers.isNullOrEmpty()) {
                beers.add(recommandBeer)
            }
            notifyActionEvent(HomeActionEntity.UpdateList(beers))
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
                filter = _beerFilter.value.orEmpty(),
                title = stringProvider.getStringRes(HomeStringProvider.Code.STYLE),
                eventNotifier = this@HomeViewModel
            )

        if (awardResponse.beers.isEmpty()) {
            return null
        }

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
            filter = BeerFilter(styleFilter = _beerFilter.value?.styleFilter),
            cursor = cursor.value
        )?.beers.orEmpty()
            .getMapper(
                sortType = _sortType.value,
                type = HomeStringProvider.Code.STYLE,
                filter = _beerFilter.value.orEmpty(),
                title = stringProvider.getStringRes(HomeStringProvider.Code.STYLE),
                eventNotifier = this@HomeViewModel
            )
    }

    private suspend fun fetchAroma(): BeerListItemViewModel {
        return beerRepository.getBeerList(
            sortType = _sortType.value?.value,
            filter = BeerFilter(aromaFilter = _beerFilter.value?.aromaFilter),
            cursor = cursor.value
        )?.beers.orEmpty().getMapper(
            sortType = _sortType.value,
            type = HomeStringProvider.Code.AROMA,
            filter = _beerFilter.value.orEmpty(),
            title = stringProvider.getStringRes(HomeStringProvider.Code.AROMA),
            eventNotifier = this@HomeViewModel
        )
    }

    private suspend fun fetchRecommand(): BeerListItemViewModel {
        return beerRepository.getBeerList(
            sortType = _sortType.value?.value,
            filter = null,
            cursor = cursor.value
        )?.beers.orEmpty().getMapper(
            sortType = _sortType.value,
            type = HomeStringProvider.Code.RANDOM,
            filter = _beerFilter.value.orEmpty(),
            title = stringProvider.getStringRes(HomeStringProvider.Code.RANDOM),
            eventNotifier = this@HomeViewModel
        )
    }

    fun loadMore2() {
        viewModelScope.launch {
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


    private fun filterSort() {
        viewModelScope.launch {
            sortSetting.getSort()
                .combine(filterSetting.getBeerFilterFlow()) { type, filter ->
                    _sortType.value = type
                    _beerFilter.value = filter
                    notifyActionEvent(HomeActionEntity.FilterSetting(_beerFilter.value))
                }
                .onStart { delay(200) }
                .collect {
                    cursor.value = 0
                }
        }
    }

    fun updateFilter(item: String, tag: String) {
        viewModelScope.launch(Dispatchers.IO) {
            Timber.d("Current Thread on UpdateFilter ${Thread.currentThread()} :: ${Thread.currentThread().name}")
            val filter = filterSetting.beerFilter

            val styleValue = filter.styleFilter?.toMutableList()
            val aromaValue = filter.aromaFilter?.toMutableList()
            val countryValue = filter.countryFilter?.toMutableList()
            var abvValue = filter.abvFilter
            Timber.d(" Tag: $tag ::  styleValue $styleValue aromaValue $aromaValue countryValue $countryValue abvValue $abvValue")
            when (tag) {
                "style" -> removeContainsItem(item, styleValue)
                "aroma" -> removeContainsItem(item, aromaValue)
                "country" -> removeContainsItem(item, countryValue)
                "abv" -> {
                    abvValue ?: return@launch
                    abvValue = null
                }
                else -> return@launch
            }

            filterSetting.beerFilter = BeerFilter(
                styleValue,
                aromaValue,
                abvValue,
                countryValue
            )

        }
    }

    private fun fetchFavorite(beer: BeerItemViewModel) {
        viewModelScope.launch {
            beer.updateFavorite()
            beerRepository.postFavorite(beer.id, beer.isFavorite.get())
        }
    }

    override fun handleActionEvent(entity: ActionEntity) {
        when (entity) {
            is HomeActionEntity.LoadMore -> {
                loadMore2()
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