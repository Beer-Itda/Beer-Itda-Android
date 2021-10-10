package com.ddd4.synesthesia.beer.presentation.ui.common.beer.item

import androidx.databinding.ObservableBoolean
import com.ddd4.synesthesia.beer.data.model.Review
import com.ddd4.synesthesia.beer.ext.toggle
import com.ddd4.synesthesia.beer.presentation.base.event.SelectEventNotifier
import com.ddd4.synesthesia.beer.presentation.commom.entity.BeerClickEntity

class BeerItemViewModel(
    val id: Int,
    val alcoholByVolume: Float,
    val aromas: List<String>,
    val beerStyle: String,
    val brewery: String,
    val country: String,
    val imageUrl: List<String>? = emptyList(),
    val name: String,
    val rateAvg: Float,
    val reviews: List<Review>? = emptyList(),
    val thumbnailImage: String,
    initFavorite: Boolean,
    var eventNotifier: SelectEventNotifier
) {
    var isFavorite: ObservableBoolean = ObservableBoolean(initFavorite)


    fun updateFavorite() {
        isFavorite.set(isFavorite.get().toggle())
    }

    fun clickItem() {
        eventNotifier.notifySelectEvent(BeerClickEntity.SelectBeer(this))
    }

    fun clickFavorite() {
        eventNotifier.notifySelectEvent(BeerClickEntity.SelectFavorite2(this))
    }
}