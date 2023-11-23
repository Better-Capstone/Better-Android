package com.ssu.better.entity.task

import com.google.gson.annotations.SerializedName

data class TaskGroup(
    @SerializedName("id")
    val id: Long,
    @SerializedName("status")
    val status: String,
    @SerializedName("startDate")
    val startDate: String,
    @SerializedName("endDate")
    val endDate: String,
)
