package com.ssu.better.entity.user

import com.google.gson.annotations.SerializedName
import com.ssu.better.entity.study.SimpleStudy
import com.ssu.better.entity.task.Task

data class UserRankHistory(
    @SerializedName("id")
    val id: Long,

    @SerializedName("study")
    val study: SimpleStudy,

    @SerializedName("score")
    val score: Int,

    @SerializedName("description")
    val description: String,

    @SerializedName("task")
    val task: Task,

    @SerializedName("createdAt")
    val createdAt: String,

    @SerializedName("updatedAt")
    val updatedAt: String,
)
