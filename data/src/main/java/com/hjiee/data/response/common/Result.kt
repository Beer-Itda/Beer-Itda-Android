package com.hjiee.data.response.common

import com.google.gson.annotations.SerializedName

data class Result<ELEMENT>(
    @SerializedName("result")
    val result: ELEMENT?
)