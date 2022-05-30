package com.hjiee.data.response.v2

import com.google.gson.annotations.SerializedName

data class StyleListResponse(
    @SerializedName("data")
    val styleList: List<StyleLargeCategoryResponse>?
)

data class StyleLargeCategoryResponse(
    @SerializedName("name")
    val largeName: String?,
    @SerializedName("id")
    val largeId: Int?,
    @SerializedName("style_mid")
    val middleCategories: List<StyleMiddleCategoryResponse>?
)

data class StyleMiddleCategoryResponse(
    @SerializedName("style_small")
    val smallCategories: List<StyleSmallCategoryResponse>?,
    @SerializedName("id")
    val middleId: Int?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("name")
    val middleName: String?
)

data class StyleSmallCategoryResponse(
    @SerializedName("id")
    val smallId: Int?,
    @SerializedName("name")
    val smallName: String?,
    @SerializedName("is_selected")
    val isSelected: Boolean?
)