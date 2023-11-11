package com.ssu.better.entity.error

import com.google.gson.annotations.SerializedName

data class NotFoundErrorBody(
    @SerializedName("time")
    val time: String,

    @SerializedName("status")
    val status: String,

    @SerializedName("requestURI")
    val requestUri: String,

    @SerializedName("data")
    val data: String,
)
