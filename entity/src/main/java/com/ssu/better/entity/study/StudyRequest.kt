package com.ssu.better.entity.study

import com.google.gson.annotations.SerializedName

data class StudyRequest(
    @SerializedName("title")
    val title: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("status")
    val status: StudyStatus,

    @SerializedName("period")
    val studyPeriod: StudyPeriod,

    @SerializedName("checkDay")
    val checkDay: StudyCheckDay,

    @SerializedName("maximumCount")
    val maxCount: Int,

    @SerializedName("minRank")
    val minRank: Int,
)
