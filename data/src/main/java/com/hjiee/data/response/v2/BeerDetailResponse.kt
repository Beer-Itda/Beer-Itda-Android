package com.hjiee.data.response.v2

import com.google.gson.annotations.SerializedName

data class BeerDetailResponse(
    @SerializedName("beer")
    val beerDetail: BeerResponse?,
    @SerializedName("same_aroma_beers")
    val sameAromaBeers: List<BeerResponse>?,
    @SerializedName("same_style_beers")
    val sameStyleBeers: List<BeerResponse>?,
    @SerializedName("review")
    val review: BeerDetailReviewResponse?,
)

data class BeerDetailReviewResponse(
    @SerializedName("beer_review_count")
    val reviewCount: Float?,
    @SerializedName("user_review")
    val myReview: ReviewResponse?,
    @SerializedName("beer_review")
    val beerReviewList: List<ReviewResponse>?,
)

data class Aroma(
    val a1: String,
    val a2: Int,
    val a3: Int,
    val a4: Int
)