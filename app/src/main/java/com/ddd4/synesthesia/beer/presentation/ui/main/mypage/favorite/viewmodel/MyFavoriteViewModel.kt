package com.ddd4.synesthesia.beer.presentation.ui.main.mypage.favorite.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ddd4.synesthesia.beer.presentation.base.BaseViewModel
import com.ddd4.synesthesia.beer.presentation.ui.common.beer.item.BeerItemViewModelMapper.getBeerItemViewModel
import com.ddd4.synesthesia.beer.presentation.ui.main.mypage.favorite.item.MyFavoriteItemViewModel
import com.ddd4.synesthesia.beer.presentation.ui.main.mypage.favorite.model.MyFavoriteActionEntity
import com.hjiee.core.event.entity.ItemClickEntity
import com.hjiee.core.util.log.L
import com.hjiee.domain.entity.DomainEntity.Beer
import com.hjiee.domain.usecase.mypage.MyFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyFavoriteViewModel @Inject constructor(
    private val useCase: MyFavoriteUseCase
) : BaseViewModel() {

    private val _isRefresh = MutableLiveData<Boolean>(false)
    val isRefresh: LiveData<Boolean> get() = _isRefresh

    init {
        load()
    }

    fun load() {
        viewModelScope.launch(errorHandler) {
            runCatching {
                useCase.execute()?.beers.orEmpty().getBeerItemViewModel(this@MyFavoriteViewModel)
            }.onSuccess {
                val itemViewModel = it.map { beer -> MyFavoriteItemViewModel(beer) }
                notifyActionEvent(MyFavoriteActionEntity.UpdateUi(itemViewModel))
            }.onFailure {
                L.e(it)
            }
            setRefresh(false)
        }
    }

    fun refresh() {
        setRefresh(true)
        load()
    }

    private fun fetchFavorite(beer: Beer) {
        viewModelScope.launch(errorHandler) {
//            beer.updateFavorite()
//            beerRepository.postFavorite(beer.id, beer.isFavorite.get())
        }
    }

    override fun handleSelectEvent(entity: ItemClickEntity) {
        when (entity) {
        }
    }

    private fun setRefresh(status: Boolean) {
        _isRefresh.value = status
    }

}