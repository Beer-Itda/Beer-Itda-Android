package com.ddd4.synesthesia.beer.presentation.ui.detail.viewmodel

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.ddd4.synesthesia.beer.presentation.base.BaseViewModel
import com.ddd4.synesthesia.beer.presentation.commom.entity.BeerClickEntity
import com.ddd4.synesthesia.beer.presentation.ui.detail.entity.BeerDetailActionEntity
import com.ddd4.synesthesia.beer.presentation.ui.detail.entity.BeerDetailItemSelectEntity
import com.ddd4.synesthesia.beer.presentation.ui.detail.item.BeerDetailItemMapper.findDetailInformation
import com.ddd4.synesthesia.beer.presentation.ui.detail.item.BeerDetailItemMapper.findDetailRelatedBeer
import com.ddd4.synesthesia.beer.presentation.ui.detail.item.BeerDetailItemMapper.getDetailViewData
import com.ddd4.synesthesia.beer.presentation.ui.detail.item.IBeerDetailViewModel
import com.ddd4.synesthesia.beer.presentation.ui.detail.view.BeerDetailStringProvider
import com.ddd4.synesthesia.beer.util.KEY_BEER_ID
import com.hjiee.core.event.entity.ItemClickEntity
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

    /**
     * @param isRelated 맥주상세에서 클릭시 연관된 맥주에 같은 데이터 좋아요 데이터를 변경
     */
    private fun fetchFavorite(id: Int, isRelated: Boolean) {
        viewModelScope.launch {
            runCatching {
                useCase.favorite.execute(id)
            }.onSuccess {
                updateInformationBeerFavorite(id)
                updateBeerDetailFavorite(id)
                if (isRelated.not()) {
                    updateRelatedBeerFavorite(beerId)
                }
            }.onFailure {
                L.e(it)
            }
        }
    }

    override fun handleSelectEvent(entity: ItemClickEntity) {
        when (entity) {
            // 연관 맥주 클릭 이벤트 처리
            is BeerClickEntity.ClickFavorite -> {
                fetchFavorite(entity.beer.id, true)
            }
        }
    }

    /**
     * 맥주 상세 favorite 상태 변경
     */
    private fun updateBeerDetailFavorite(id: Int) {
        if (id == beerId) {
            isFavorite.set(isFavorite.get().toggle())
        }
    }

    /**
     * information에 추가된 beer model의 favorite 상태 변경
     */
    private fun updateInformationBeerFavorite(id: Int) {
        if (id == beerId) {
            val info = item.findDetailInformation()
            info.beer.updateFavorite()
        }
    }

    /**
     * 연관 데이터에 추가된 beer model의 favorite 상태 변경
     */
    private fun updateRelatedBeerFavorite(id: Int) {
        val related = item.findDetailRelatedBeer()
        related.map { it.updateFavorite(id) }
    }

    fun clickFavorite() {
        fetchFavorite(beerId, false)
    }

    fun clickReviewAll() {
        notifySelectEvent(BeerDetailItemSelectEntity.ReviewAll)
    }
}