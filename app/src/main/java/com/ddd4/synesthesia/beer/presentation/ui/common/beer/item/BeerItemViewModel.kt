package com.ddd4.synesthesia.beer.presentation.ui.common.beer.item

import androidx.databinding.ObservableBoolean
import com.ddd4.synesthesia.beer.data.model.Beer
import com.ddd4.synesthesia.beer.data.model.Review
import com.ddd4.synesthesia.beer.ext.toggle
import com.ddd4.synesthesia.beer.presentation.base.event.SelectEventNotifier
import com.ddd4.synesthesia.beer.presentation.commom.entity.BeerClickEntity
import com.ddd4.synesthesia.beer.presentation.ui.home.item.child.IHomeItemChildViewModel

class BeerItemViewModel(
    val abv: Float,
    val aromas: List<String>,
    val beerStyle: String,
    val brewery: String,
    val country: String,
    val id: Int,
    val imageUrl: List<String>,
    val name: String,
    val rateAvg: Float,
    val rateOwnerBeerId: Int,
    val rateOwnerRatio: Float,
    val rateOwnerUserId: Int,
    val reviews: List<Review>,
    val thumbnailImage: String,
    val reviewOwnerBeer: Beer?,
    val reviewOwnerContent: String,
    val reviewOwnerNickname: String,
    val reviewOwnerRatio: Float,
    val reviewOwnerUserId: Int,
    initFavorite: Boolean
) : IHomeItemChildViewModel {
    var isFavorite: ObservableBoolean = ObservableBoolean(initFavorite)
    var eventNotifier: SelectEventNotifier? = null

    fun updateFavorite() {
        isFavorite.set(isFavorite.get().toggle())
    }

    fun clickItem() {
        eventNotifier?.notifySelectEvent(BeerClickEntity.SelectItem2(this))
    }

    fun clickFavorite() {
        eventNotifier?.notifySelectEvent(BeerClickEntity.SelectFavorite2(this))
    }
}