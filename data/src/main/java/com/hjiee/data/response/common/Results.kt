package com.hjiee.data.response.common

import com.google.gson.annotations.SerializedName

data class Results<ELEMENT>(
    @SerializedName("result")
    val results: List<ELEMENT>?
)