package com.ddd4.synesthesia.beer.presentation.ui.detail.viewmodel

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.ddd4.synesthesia.beer.presentation.base.BaseViewModel
import com.ddd4.synesthesia.beer.presentation.ui.detail.entity.BeerDetailActionEntity
import com.ddd4.synesthesia.beer.presentation.ui.detail.entity.BeerDetailItemSelectEntity
import com.ddd4.synesthesia.beer.presentation.ui.detail.item.BeerDetailItemMapper.findDetailInformation
import com.ddd4.synesthesia.beer.presentation.ui.detail.item.BeerDetailItemMapper.findDetailRelatedBeer
import com.ddd4.synesthesia.beer.presentation.ui.detail.item.BeerDetailItemMapper.getDetailViewData
import com.ddd4.synesthesia.beer.presentation.ui.detail.item.IBeerDetailViewModel
import com.ddd4.synesthesia.beer.presentation.ui.detail.view.BeerDetailStringProvider
import com.ddd4.synesthesia.beer.util.KEY_BEER_ID
import com.hjiee.core.ext.orFalse
import com.hjiee.core.ext.orZero
import com.hjiee.core.ext.toggle
import com.hjiee.core.util.log.L
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BeerDetailViewModel @Inject constructor(
    private val useCase: BeerDetailUseCaseGroup,
    private val stringProvider: BeerDetailStringProvider,
    private val savedState: SavedStateHandle
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
                L.e(it)
            }
        }
    }

    private fun fetchFavorite() {
        viewModelScope.launch(errorHandler) {
            runCatching {
                useCase.favorite.execute(beerId)
            }.onSuccess {
                updateInformationBeerFavorite()
                updateRelatedBeerFavorite()
                updateBeerDetailFavorite()
            }.onFailure {
                L.e(it)
            }
        }
    }

    /**
     * 맥주 상세 favorite 상태 변경
     */
    private fun updateBeerDetailFavorite() {
        isFavorite.set(isFavorite.get().toggle())
    }

    /**
     * information에 추가된 beer model의 favorite 상태 변경
     */
    private fun updateInformationBeerFavorite() {
        val info = item.findDetailInformation()
        info.beer.updateFavorite()
    }

    /**
     * 연관 데이터에 추가된 beer model의 favorite 상태 변경
     */
    private fun updateRelatedBeerFavorite() {
        val related = item.findDetailRelatedBeer()
        related.map { it.updateFavorite(beerId) }
    }

    fun clickFavorite() {
        fetchFavorite()
    }

    fun clickReviewAll() {
        notifySelectEvent(BeerDetailItemSelectEntity.ReviewAll)
    }
}