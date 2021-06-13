package com.ddd4.synesthesia.beer.presentation.ui.detail.viewmodel

import android.provider.Contacts
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.ddd4.synesthesia.beer.data.model.Beer
import com.ddd4.synesthesia.beer.data.model.RelatedBeers
import com.ddd4.synesthesia.beer.domain.repository.BeerRepository
import com.ddd4.synesthesia.beer.ext.ChannelType
import com.ddd4.synesthesia.beer.ext.CoroutinesEvent
import com.ddd4.synesthesia.beer.ext.orFalse
import com.ddd4.synesthesia.beer.presentation.base.BaseViewModel
import com.ddd4.synesthesia.beer.presentation.ui.detail.entity.DetailItemSelectEntity
import com.ddd4.synesthesia.beer.presentation.ui.detail.view.DetailStringProvider
import com.ddd4.synesthesia.beer.util.KeyStringConst.Companion.KEY_BEER_ID
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import timber.log.Timber

class DetailViewModel @ViewModelInject constructor(
    private val beerRepository: BeerRepository,
    private val stringProvider: DetailStringProvider,
    @Assisted private val savedState: SavedStateHandle
) : BaseViewModel() {

    private val _id = MutableLiveData<Int>(savedState.get(KEY_BEER_ID))
    val id: LiveData<Int> get() = _id

    private val _beer = MutableLiveData<Beer>()
    val beer: LiveData<Beer> get() = _beer

    private val _relatedBeers = MutableLiveData<RelatedBeers>()
    val relatedBeers: LiveData<RelatedBeers> get() = _relatedBeers

    private val handler = CoroutineExceptionHandler { coroutineContext, throwable ->
        Timber.e(throwable)
    }

    fun load() {
        statusLoading()
        viewModelScope.launch(handler) {
            _id.value?.let {
                val response = beerRepository.getBeer(it)
                _beer.value = response?.beer?.apply {
                    setFavorite()
                    eventNotifier = this@DetailViewModel
                }
                _relatedBeers.value = response?.relatedBeers?.apply {
                    aromaRelated?.map { it.eventNotifier = this@DetailViewModel }
                    randomlyRelated?.map { it.eventNotifier = this@DetailViewModel }
                    styleRelated?.map { it.eventNotifier = this@DetailViewModel }
                }
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
                _beer.value?.updateFavorite()
                beerRepository.postFavorite(it, _beer.value?.isFavorite?.get().orFalse())
            } ?: kotlin.run {
                throwMessage(stringProvider.getError(), true)
            }
        }
    }

    @ExperimentalCoroutinesApi
    fun clickFavorite() {
        fetchFavorite()
        _beer.value?.let {
            viewModelScope.launch(Dispatchers.Main) {
                CoroutinesEvent.publish(ChannelType.Favorite(it))
            }
        }
    }

    fun clickReviewAll() {
        notifySelectEvent(DetailItemSelectEntity.ReviewAll)
    }

    fun clickStarRate() {
        notifySelectEvent(DetailItemSelectEntity.StarRate)
    }
}