package com.hjiee.data.response.v2

import com.google.gson.annotations.SerializedName

data class StyleLargeCategoryResponse(
    @SerializedName("big_name")
    val largeName: String?,
    @SerializedName("id")
    val largeId: Int?,
    @SerializedName("Style_Mids")
    val middleCategories: List<StyleMiddleCategoryResponse>?
)

data class StyleMiddleCategoryResponse(
    @SerializedName("Style_Smalls")
    val smallCategories: List<StyleSmallCategoryResponse>?,
    @SerializedName("big_style_id")
    val largeId: Int?,
    @SerializedName("id")
    val middleId: Int?,
    @SerializedName("mid_description")
    val description: String?,
    @SerializedName("mid_image")
    val middleImage: String?,
    @SerializedName("mid_name")
    val middleName: String?
)

data class StyleSmallCategoryResponse(
    @SerializedName("id")
    val smallId: Int?,
    @SerializedName("mid_style_id")
    val middleId: Int?,
    @SerializedName("small_name")
    val smallName: String?
)