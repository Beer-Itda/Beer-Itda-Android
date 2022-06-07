package com.hjiee.presentation.ui.common.beer.item

import androidx.databinding.ObservableBoolean
import com.hjiee.presentation.commom.entity.BeerClickEntity
import com.hjiee.core.event.SelectEventNotifier
import com.hjiee.core.ext.toggle
import com.hjiee.domain.entity.DomainEntity

class BeerItemViewModel(
    val id: Int,
    val alcoholByVolume: Float,
    val aromas: List<String> = emptyList(),
    val style: String,
    val brewery: String,
    val country: String,
    val imageUrl: List<String>? = emptyList(),
    val nameForEnglish: String,
    val nameForKorean: String,
    val starAvg: Float,
    val reviews: List<DomainEntity.Review>? = emptyList(),
    val thumbnailImage: String,
    _isFavorite: Boolean,
    var eventNotifier: SelectEventNotifier? = null
) {
    var isFavorite: ObservableBoolean = ObservableBoolean(_isFavorite)


    fun updateFavorite() {
        isFavorite.set(isFavorite.get().toggle())
    }

    fun clickBeer() {
        eventNotifier?.notifySelectEvent(BeerClickEntity.ClickBeer(this))
    }

    fun clickFavorite() {
        updateFavorite()
        eventNotifier?.notifySelectEvent(BeerClickEntity.ClickFavorite(this))
    }
}