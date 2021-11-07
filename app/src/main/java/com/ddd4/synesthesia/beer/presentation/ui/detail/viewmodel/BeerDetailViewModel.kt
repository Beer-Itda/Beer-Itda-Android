package com.ddd4.synesthesia.beer.presentation.ui.detail.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.ddd4.synesthesia.beer.domain.repository.BeerRepository
import com.ddd4.synesthesia.beer.ext.EventFlow
import com.ddd4.synesthesia.beer.ext.GlobalEvent
import com.ddd4.synesthesia.beer.ext.orFalse
import com.ddd4.synesthesia.beer.presentation.base.BaseViewModel
import com.ddd4.synesthesia.beer.presentation.ui.common.beer.item.BeerItemViewModelMapper.getBeerItemViewModel
import com.ddd4.synesthesia.beer.presentation.ui.detail.entity.BeerDetailActionEntity
import com.ddd4.synesthesia.beer.presentation.ui.detail.entity.BeerDetailItemSelectEntity
import com.ddd4.synesthesia.beer.presentation.ui.detail.item.BeerDetailItemMapper.getBeerDetailItemViewModel
import com.ddd4.synesthesia.beer.presentation.ui.detail.item.BeerDetailItemMapper.getDetailViewData
import com.ddd4.synesthesia.beer.presentation.ui.detail.item.BeerDetailItemViewModel
import com.ddd4.synesthesia.beer.presentation.ui.detail.item.IBeerDetailViewModel
import com.ddd4.synesthesia.beer.presentation.ui.detail.view.BeerDetailStringProvider
import com.ddd4.synesthesia.beer.util.KEY_BEER_ID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BeerDetailViewModel @ViewModelInject constructor(
    private val beerRepository: BeerRepository,
    private val stringProvider: BeerDetailStringProvider,
    @Assisted private val savedState: SavedStateHandle
) : BaseViewModel() {

    private val beerId by lazy { savedState.get(KEY_BEER_ID) as? Int }

    private val _item = MutableLiveData<BeerDetailItemViewModel>()
    val item: LiveData<BeerDetailItemViewModel> get() = _item

    fun load() {
        statusLoading()
        viewModelScope.launch(errorHandler) {
            beerId?.let {
                val response = beerRepository.getBeer(it)
                _item.value = response?.getBeerDetailItemViewModel(eventNotifier = this@BeerDetailViewModel)
                val items: List<IBeerDetailViewModel> = _item.value?.getDetailViewData().orEmpty()

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
            _item.value?.let { item ->
                item.beer.updateFavorite()
                beerRepository.postFavorite(item.beer.id, item.beer.isFavorite.get().orFalse())
            } ?: kotlin.run {
                throwMessage(stringProvider.getError(), true)
            }
        }
    }

    fun clickFavorite() {
        fetchFavorite()
        _item.value?.let { item ->
            viewModelScope.launch(Dispatchers.Main) {
                EventFlow.post(GlobalEvent.Favorite(item.beer.id))
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