package com.ssu.better.entity.study

import com.google.gson.annotations.SerializedName

data class StudyRequest(
    @SerializedName("categoryId")
    val categoryId: Long,

    @SerializedName("title")
    val title: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("checkDay")
    val checkDay: StudyCheckDay,

    @SerializedName("kickCondition")
    val kickCondition: Int,

    @SerializedName("maximumCount")
    val maximumCount: Int,

    @SerializedName("minRank")
    val minRank: Int,

    @SerializedName("period")
    val period: StudyPeriod
)
