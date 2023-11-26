package com.ssu.better.entity.task

import com.google.gson.annotations.SerializedName
import com.ssu.better.entity.study.Status

data class TaskGroup(
    @SerializedName("id")
    val taskGroupId: Long,

    @SerializedName("status")
    val status: Status,

    @SerializedName("startDate")
    val startDate: String,

    @SerializedName("endDate")
    val endDate: String,

    @SerializedName("createdAt")
    val createdAt: String,

    @SerializedName("updatedAt")
    val updatedAt: String,
}
