package com.ddd4.synesthesia.beer.presentation.ui.detail.viewmodel

import BeerDetailItemMapper.getDetailViewData
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.ddd4.synesthesia.beer.presentation.base.BaseViewModel
import com.ddd4.synesthesia.beer.presentation.ui.detail.entity.BeerDetailActionEntity
import com.ddd4.synesthesia.beer.presentation.ui.detail.entity.BeerDetailItemSelectEntity
import com.ddd4.synesthesia.beer.presentation.ui.detail.item.IBeerDetailViewModel
import com.ddd4.synesthesia.beer.presentation.ui.detail.view.BeerDetailStringProvider
import com.ddd4.synesthesia.beer.util.KEY_BEER_ID
import com.hjiee.core.ext.orZero
import kotlinx.coroutines.launch

class BeerDetailViewModel @ViewModelInject constructor(
    private val useCase: BeerDetailUseCaseGroup,
    private val stringProvider: BeerDetailStringProvider,
    @Assisted private val savedState: SavedStateHandle
) : BaseViewModel() {

    private val beerId by lazy { (savedState.get(KEY_BEER_ID) as? Int).orZero() }

    private val _item = MutableLiveData<List<IBeerDetailViewModel>>()
    val item: LiveData<List<IBeerDetailViewModel>> get() = _item

    fun load() {
        statusLoading()
        viewModelScope.launch {
            runCatching {
                _item.value = useCase.getBeerDetail.execute(beerId)
                    .getDetailViewData(this@BeerDetailViewModel)
            }.onSuccess {
                statusSuccess()
                notifyActionEvent(BeerDetailActionEntity.UpdateUi(_item.value.orEmpty()))
            }.onFailure {
                statusFailure()
                throwMessage(stringProvider.getError(), true)
            }
        }
    }

    private fun fetchFavorite() {
        viewModelScope.launch(errorHandler) {
//            _item.value?.let { item ->
//                item.beer.updateFavorite()
//                beerRepository.postFavorite(item.beer.id, item.beer.isFavorite.get().orFalse())
//            } ?: kotlin.run {
//                throwMessage(stringProvider.getError(), true)
//            }
        }
    }

    fun clickFavorite() {
        fetchFavorite()
//        _item.value?.let { item ->
//            viewModelScope.launch(Dispatchers.Main) {
//                EventFlow.post(GlobalEvent.Favorite(item.beer.id))
//            }
//        }
    }

    fun clickReviewAll() {
        notifySelectEvent(BeerDetailItemSelectEntity.ReviewAll)
    }

    fun clickStarRate() {
        notifySelectEvent(BeerDetailItemSelectEntity.StarRate)
    }
}