package com.ddd4.synesthesia.beer.presentation.ui.detail.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.ddd4.synesthesia.beer.data.model.RelatedBeers
import com.ddd4.synesthesia.beer.domain.repository.BeerRepository
import com.ddd4.synesthesia.beer.ext.ChannelType
import com.ddd4.synesthesia.beer.ext.CoroutinesEvent
import com.ddd4.synesthesia.beer.ext.orFalse
import com.ddd4.synesthesia.beer.presentation.base.BaseViewModel
import com.ddd4.synesthesia.beer.presentation.ui.detail.entity.BeerDetailActionEntity
import com.ddd4.synesthesia.beer.presentation.ui.detail.entity.BeerDetailItemSelectEntity
import com.ddd4.synesthesia.beer.presentation.ui.detail.item.BeerDetailItemMapper.getBeerDetailItemViewModel
import com.ddd4.synesthesia.beer.presentation.ui.detail.item.BeerDetailItemViewModel
import com.ddd4.synesthesia.beer.presentation.ui.detail.item.IBeerDetailViewModel
import com.ddd4.synesthesia.beer.presentation.ui.detail.view.BeerDetailStringProvider
import com.ddd4.synesthesia.beer.util.KEY_BEER_ID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

class BeerDetailViewModel @ViewModelInject constructor(
    private val beerRepository: BeerRepository,
    private val stringProvider: BeerDetailStringProvider,
    @Assisted private val savedState: SavedStateHandle
) : BaseViewModel() {

    private val _id = MutableLiveData<Int>(savedState.get(KEY_BEER_ID))
    val id: LiveData<Int> get() = _id

    private val _item = MutableLiveData<BeerDetailItemViewModel>()
    val item: LiveData<BeerDetailItemViewModel> get() = _item

    private val _relatedBeers = MutableLiveData<RelatedBeers>()
    val relatedBeers: LiveData<RelatedBeers> get() = _relatedBeers

    fun load() {
        statusLoading()
        viewModelScope.launch(errorHandler) {
            _id.value?.let {
                val response = beerRepository.getBeer(it)

                val items: List<IBeerDetailViewModel> = response
                    ?.getBeerDetailItemViewModel(eventNotifier = this@BeerDetailViewModel)
                    .orEmpty()

//                _beer.value = response?.beer?.getBeerItemViewModel(eventNotifier = this@DetailViewModel)
//                _relatedBeers.value = response?.relatedBeers?.apply {
//                    aromaRelated?.map { it.eventNotifier = this@DetailViewModel }
//                    randomlyRelated?.map { it.eventNotifier = this@DetailViewModel }
//                    styleRelated?.map { it.eventNotifier = this@DetailViewModel }
//                }
                notifyActionEvent(BeerDetailActionEntity.UpdateUi(items))
                statusSuccess()
            } ?: kotlin.run {
                throwMessage(stringProvider.getError(), true)
                statusFailure()
            }
        }
    }

    private fun fetchFavorite() {
        viewModelScope.launch(errorHandler) {
            _id.value?.let {
                _item.value?.beer?.updateFavorite()
                beerRepository.postFavorite(it, _item.value?.beer?.isFavorite?.get().orFalse())
            } ?: kotlin.run {
                throwMessage(stringProvider.getError(), true)
            }
        }
    }

    @ExperimentalCoroutinesApi
    fun clickFavorite() {
        fetchFavorite()
        _item.value?.let {
            viewModelScope.launch(Dispatchers.Main) {
                CoroutinesEvent.publish(ChannelType.Favorite(it.beer))
            }
        }
    }

    fun clickReviewAll() {
        notifySelectEvent(BeerDetailItemSelectEntity.ReviewAll)
    }

    fun clickStarRate() {
        notifySelectEvent(BeerDetailItemSelectEntity.StarRate)
    }
}