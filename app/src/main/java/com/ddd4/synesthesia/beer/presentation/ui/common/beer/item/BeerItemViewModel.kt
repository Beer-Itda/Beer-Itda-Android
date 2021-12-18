package com.ddd4.synesthesia.beer.presentation.ui.common.beer.item

import androidx.databinding.ObservableBoolean
import com.hjiee.core.event.SelectEventNotifier
import com.ddd4.synesthesia.beer.presentation.commom.entity.BeerClickEntity
import com.hjiee.domain.entity.DomainEntity

class BeerItemViewModel(
    val id: Int,
    val alcoholByVolume: Float,
    val aromas: List<String> = emptyList(),
//    val beerStyle: String,
//    val brewery: String,
//    val country: String,
    val imageUrl: List<String>? = emptyList(),
    val name: String,
    val starAvg: Float,
    val reviews: List<DomainEntity.Review>? = emptyList(),
    val thumbnailImage: String,
//    initFavorite: Boolean,
    var eventNotifier: SelectEventNotifier
) {
//    var isFavorite: ObservableBoolean = ObservableBoolean(initFavorite)


    fun updateFavorite() {
//        isFavorite.set(isFavorite.get().toggle())
    }

    fun clickItem() {
        eventNotifier.notifySelectEvent(BeerClickEntity.SelectBeer(this))
    }

    fun clickFavorite() {
        eventNotifier.notifySelectEvent(BeerClickEntity.SelectFavorite2(this))
    }
}