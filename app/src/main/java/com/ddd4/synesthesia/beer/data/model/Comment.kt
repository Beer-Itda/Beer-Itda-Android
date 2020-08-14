package com.ddd4.synesthesia.beer.data.model


import com.google.gson.annotations.SerializedName

data class Comment(
    @SerializedName("beer_id")
    val beerId: Int,
    @SerializedName("content")
    val content: String,
    @SerializedName("user_id")
    val userId: Int
)