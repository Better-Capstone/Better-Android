package com.ssu.better.entity.error

import com.google.gson.annotations.SerializedName

data class ValidationErrorData(
    @SerializedName("field")
    val field: String,

    @SerializedName("message")
    val message: String,
)
