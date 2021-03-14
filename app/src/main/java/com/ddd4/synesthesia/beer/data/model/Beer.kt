package com.ddd4.synesthesia.beer.data.model


import android.os.Parcelable
import androidx.databinding.ObservableBoolean
import com.ddd4.synesthesia.beer.ext.orFalse
import com.ddd4.synesthesia.beer.ext.toggle
import com.ddd4.synesthesia.beer.presentation.base.event.SelectEventNotifier
import com.ddd4.synesthesia.beer.presentation.commom.entity.BeerClickEntity
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Beer(
    @SerializedName("abv")
    val abv: Float? = null,
    @SerializedName("aroma")
    val aromas: List<String>? = null,
    @SerializedName("beer_style")
    val beerStyle: String? = null,
    @SerializedName("brewery")
    val brewery: String? = null,
    @SerializedName("country")
    val country: String? = null,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image_url")
    val imageUrl: List<String>? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("rate_avg")
    val rateAvg: Float? = null,
    @SerializedName("rate_owner")
    val rateOwner: RateOwner? = null,
    @SerializedName("reviews")
    val reviews: List<Review>? = null,
    @SerializedName("thumbnail_image")
    val thumbnailImage: String? = null,
    @SerializedName("review_owner")
    val reviewOwner: ReviewOwner? = null,
    @SerializedName("favorite_flag")
    var favoriteFlag: Boolean? = false
) : Parcelable {
    var isFavorite: ObservableBoolean = ObservableBoolean(false)
    var eventNotifier: SelectEventNotifier? = null

    fun setFavorite() {
        isFavorite = ObservableBoolean(favoriteFlag.orFalse())
    }

    fun updateFavorite() {
        favoriteFlag = favoriteFlag?.toggle()
        isFavorite.set(isFavorite.get().toggle())
    }

    fun clickItem() {
        eventNotifier?.notifySelectEvent(BeerClickEntity.SelectItem(this))
    }

    fun clickFavorite() {
        eventNotifier?.notifySelectEvent(BeerClickEntity.SelectFavorite(this))
    }
}