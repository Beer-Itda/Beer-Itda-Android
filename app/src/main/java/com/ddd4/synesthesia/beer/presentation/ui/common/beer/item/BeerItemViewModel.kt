package com.ddd4.synesthesia.beer.presentation.ui.common.beer.item

import androidx.databinding.ObservableBoolean
import com.ddd4.synesthesia.beer.presentation.commom.entity.BeerClickEntity
import com.hjiee.core.event.SelectEventNotifier
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
    initFavorite: Boolean,
    var eventNotifier: SelectEventNotifier? = null
) {
    var isFavorite: ObservableBoolean = ObservableBoolean(initFavorite)


    fun updateFavorite() {
        isFavorite.set(isFavorite.get().not())
    }

    fun clickItem() {
        eventNotifier?.notifySelectEvent(BeerClickEntity.SelectBeer(this))
    }

    fun clickFavorite() {
        eventNotifier?.notifySelectEvent(BeerClickEntity.SelectFavorite2(this))
    }
}