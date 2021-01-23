package com.ddd4.synesthesia.beer.presentation.ui.home.viewmodel

import androidx.databinding.ObservableBoolean
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
import com.ddd4.synesthesia.beer.ext.orFalse
import com.ddd4.synesthesia.beer.presentation.base.BaseViewModel
import com.ddd4.synesthesia.beer.presentation.base.entity.ItemClickEntity
import com.ddd4.synesthesia.beer.presentation.commom.BeerClickEntity
import com.ddd4.synesthesia.beer.presentation.ui.home.entity.HomeSelectEntity
import com.ddd4.synesthesia.beer.util.filter.BeerFilter
import com.ddd4.synesthesia.beer.util.filter.FilterSetting
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

@ExperimentalCoroutinesApi
class HomeViewModel @ViewModelInject constructor(
    private val beerRepository: BeerRepository,
    private val sortSetting: SortSetting,
    private val filterSetting: FilterSetting,
    @Assisted val savedState : SavedStateHandle
) : BaseViewModel() {

    private val _beerList = MutableLiveData<List<Beer>?>()
    val beerList: LiveData<List<Beer>?>
        get() = _beerList

    private val _sortType = MutableLiveData<SortType>()
    val sortType: LiveData<SortType> get() = _sortType

    private val _beerFilter = MutableLiveData<BeerFilter>()
    val beerFilter: LiveData<BeerFilter> get() = _beerFilter

    val cursor = MutableLiveData(0)

    private val _isLoadMore = MutableLiveData<Boolean>(false)
    val isLoadMore : LiveData<Boolean> get() = _isLoadMore

    private val _appConfig = MutableLiveData<AppConfig>()
    val appConfig: LiveData<AppConfig> get() = _appConfig

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
        }
    }

    fun loadMore() {
        viewModelScope.launch(Dispatchers.IO) {
            cursor.value?.let {
                if(_isLoadMore.value == false) {
                    _beerList.postValue(_beerList.value?.toMutableList()?.apply { addAll(listOf(Beer(id = -1))) })
                    _isLoadMore.postValue(true)
                    load()
                }
            }
        }
    }

    fun load() {
        viewModelScope.launch {
            val response = beerRepository.getBeerList(_sortType.value?.value, _beerFilter.value,cursor.value)

            if (response?.beers.isNullOrEmpty() && cursor.value == 0)  {
                _beerList.value = response?.beers?.map { beer ->
                    beer.setFavorite()
                    beer.eventNotifier = this@HomeViewModel
                    beer
                }
            }

            cursor.value = response?.nextCursor
            if(_isLoadMore.value == true) {
                _beerList.value = (_beerList.value?.toMutableList()?.apply { _beerList.value?.let { removeAt(it.size-1) } })
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
        }
    }

    private fun filterSort() {
        viewModelScope.launch {
            sortSetting.getSort()
                .combine(filterSetting.getBeerFilterFlow()) { type, filter ->
                    _sortType.value = type
                    _beerFilter.value = filter
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

    private fun fetchFavorite(beer : Beer) {
        viewModelScope.launch {
            beer.updateFavorite()
            beerRepository.postFavorite(beer.id, beer.isFavorite.get())
        }
    }

    override fun handleSelectEvent(entity: ItemClickEntity) {
        when(entity) {
            is BeerClickEntity.SelectFavorite -> {
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
        notifySelectEvent(HomeSelectEntity.Filter)
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