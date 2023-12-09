package com.ssu.better.entity.challenge

import com.google.gson.annotations.SerializedName
import com.ssu.better.entity.user.UserScore

data class ChallengeHistory(
    @SerializedName("id")
    val id: Long,

    val task: SimpleTask,

    @SerializedName("user")
    val user: UserScore,

    @SerializedName("description")
    val description: String,

    @SerializedName("image")
    val image: String,

    @SerializedName("approveMember")
    val approveMember: ArrayList<Long>,

    @SerializedName("rejectMember")
    val rejectMember: ArrayList<Long>,

    @SerializedName("createdAt")
    val createdAt: String,

    @SerializedName("updatedAt")
    val updatedAt: String,
)

data class SimpleTask(
    @SerializedName("id")
    val id: Long,
    @SerializedName("title")
    val title: String,
    @SerializedName("createdAt")
    val createdAt: String,
)
