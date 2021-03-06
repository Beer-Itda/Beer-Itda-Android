package com.hjiee.data.response.common

import com.google.gson.annotations.SerializedName

data class NetworkResponse<ELEMENT>(
    @SerializedName("data")
    val data: ELEMENT?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("success")
    val isSuccess: Boolean?
)