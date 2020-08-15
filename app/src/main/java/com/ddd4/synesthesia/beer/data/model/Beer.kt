package com.ddd4.synesthesia.beer.data.model


import com.google.gson.annotations.SerializedName

data class Beer(
    @SerializedName("abv")
    val abv: Double,
    @SerializedName("aroma")
    val aroma: List<String>,
    @SerializedName("beer_style")
    val beerStyle: String,
    @SerializedName("brewery")
    val brewery: String,
    @SerializedName("comments")
    val comments: List<Comment>,
    @SerializedName("country")
    val country: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image_url")
    val imageUrl: List<String>,
    @SerializedName("name")
    val name: String,
    @SerializedName("rate_avg")
    val rateAvg: Double,
    @SerializedName("rate_owner")
    val rateOwner: RateOwner
) {
    val rateAvgToString: String
        get() = rateAvg.toString()

    val abvToString: String
        get() = abv.toString()
}