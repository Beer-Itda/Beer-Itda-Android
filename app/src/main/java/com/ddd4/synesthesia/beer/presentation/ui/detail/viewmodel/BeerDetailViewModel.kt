package com.ddd4.synesthesia.beer.presentation.ui.detail.viewmodel

import BeerDetailItemMapper.getDetailViewData
import androidx.databinding.ObservableBoolean
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.ddd4.synesthesia.beer.presentation.base.BaseViewModel
import com.ddd4.synesthesia.beer.presentation.ui.detail.entity.BeerDetailActionEntity
import com.ddd4.synesthesia.beer.presentation.ui.detail.entity.BeerDetailItemSelectEntity
import com.ddd4.synesthesia.beer.presentation.ui.detail.item.IBeerDetailViewModel
import com.ddd4.synesthesia.beer.presentation.ui.detail.view.BeerDetailStringProvider
import com.ddd4.synesthesia.beer.util.KEY_BEER_ID
import com.hjiee.core.ext.orFalse
import com.hjiee.core.ext.orZero
import kotlinx.coroutines.launch

class BeerDetailViewModel @ViewModelInject constructor(
    private val useCase: BeerDetailUseCaseGroup,
    private val stringProvider: BeerDetailStringProvider,
    @Assisted private val savedState: SavedStateHandle
) : BaseViewModel() {

    private val beerId by lazy { (savedState.get(KEY_BEER_ID) as? Int).orZero() }

    private var item: List<IBeerDetailViewModel> = emptyList()
    val isFavorite = ObservableBoolean(false)

    fun load() {
        statusLoading()
        viewModelScope.launch {
            runCatching {
                val detail = useCase.getBeerDetail.execute(beerId)
                isFavorite.set(detail?.beer?.isFavorite.orFalse())
                item = detail.getDetailViewData(this@BeerDetailViewModel)
            }.onSuccess {
                statusSuccess()
                notifyActionEvent(BeerDetailActionEntity.UpdateUi(item))
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
}