package com.ssu.better.entity.study

import com.google.gson.annotations.SerializedName

data class SimpleStudy(
    @SerializedName("id")
    val studyId: Long,

    @SerializedName("title")
    val title: String,
)
