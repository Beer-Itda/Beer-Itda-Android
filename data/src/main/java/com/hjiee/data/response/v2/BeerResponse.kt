package com.hjiee.data.response.v2

import com.google.gson.annotations.SerializedName
import com.hjiee.domain.entity.DomainEntity

data class BeerResponse(
    @SerializedName("e_name")
    val engName: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("k_name")
    val krName: String?,
    @SerializedName("star_avg")
    val starAvg: Float?,
    @SerializedName("thumbnail_image")
    val thumbnailImage: String?
) : DomainEntity.BeerItem