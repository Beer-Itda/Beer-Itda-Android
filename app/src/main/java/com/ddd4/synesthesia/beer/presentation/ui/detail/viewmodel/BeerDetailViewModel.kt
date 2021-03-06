package com.ddd4.synesthesia.beer.presentation.ui.detail.viewmodel

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.ddd4.synesthesia.beer.presentation.base.BaseViewModel
import com.ddd4.synesthesia.beer.presentation.commom.entity.BeerClickEntity
import com.ddd4.synesthesia.beer.presentation.ui.detail.entity.BeerDetailActionEntity
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
import com.hjiee.domain.entity.DomainEntity
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

    private val _myReview = MutableLiveData<DomainEntity.Review>()
    val myReview: LiveData<DomainEntity.Review> get() = _myReview

    fun load() {
        statusLoading()
        viewModelScope.launch {
            runCatching {
                useCase.getBeerDetail.execute(beerId)
            }.onSuccess { detail ->
                statusSuccess()
                isFavorite.set(detail?.beer?.isFavorite.orFalse())
                _myReview.value = detail?.myReview
                item = detail.getDetailViewData(this@BeerDetailViewModel)
                notifyActionEvent(BeerDetailActionEntity.UpdateUi(item))
            }.onFailure {
                statusFailure()
                throwMessage(stringProvider.getError(), true)
                L.e(it)
            }
        }
    }

    fun reviewDelete() {
        viewModelScope.launch {
            runCatching {
                useCase.deleteReview.execute(beerId)
            }.onSuccess {
                load()
                notifyActionEvent(BeerDetailActionEntity.Toast(stringProvider.getDeleteMessage()))
            }.onFailure {
                L.e(it)
            }
        }
    }

    /**
     * @param isRelated ?????????????????? ????????? ????????? ????????? ?????? ????????? ????????? ???????????? ??????
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
            // ?????? ?????? ?????? ????????? ??????
            is BeerClickEntity.ClickFavorite -> {
                fetchFavorite(entity.beer.id, true)
            }
        }
    }

    /**
     * ?????? ?????? favorite ?????? ??????
     */
    private fun updateBeerDetailFavorite(id: Int) {
        if (id == beerId) {
            isFavorite.set(isFavorite.get().toggle())
        }
    }

    /**
     * information??? ????????? beer model??? favorite ?????? ??????
     */
    private fun updateInformationBeerFavorite(id: Int) {
        if (id == beerId) {
            val info = item.findDetailInformation()
            info.beer.updateFavorite()
        }
    }

    /**
     * ?????? ???????????? ????????? beer model??? favorite ?????? ??????
     */
    private fun updateRelatedBeerFavorite(id: Int) {
        val related = item.findDetailRelatedBeer()
        related.map { it.updateFavorite(id) }
    }

    fun clickFavorite() {
        fetchFavorite(beerId, false)
    }
}