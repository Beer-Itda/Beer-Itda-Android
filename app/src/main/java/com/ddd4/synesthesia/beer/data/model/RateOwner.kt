package com.ddd4.synesthesia.beer.data.model


import com.google.gson.annotations.SerializedName

data class RateOwner(
    @SerializedName("BeerID")
    val beerID: Int,
    @SerializedName("Ratio")
    val ratio: Double,
    @SerializedName("UserID")
    val userID: Int
)