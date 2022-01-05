package com.hjiee.data.response.v2

import com.google.gson.annotations.SerializedName

data class BeerResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("brewery")
    val brewery: String?,
    @SerializedName("country")
    val country: String?,
    @SerializedName("e_name")
    val nameForEnglish: String?,
    @SerializedName("heart")
    val heart: Boolean?,
    @SerializedName("k_name")
    val nameForKorean: String?,
    @SerializedName("star_avg")
    val starAvg: Float?,
    @SerializedName("abv")
    val abv: Float?,
    @SerializedName("style")
    val style: String?,
    @SerializedName("thumbnail_image")
    val thumbnailImage: String?
//    @SerializedName("aroma")
//    val aroma: List<String>?,
)