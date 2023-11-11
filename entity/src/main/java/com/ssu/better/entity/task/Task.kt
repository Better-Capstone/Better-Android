package com.ssu.better.entity.task

import com.google.gson.annotations.SerializedName

data class Task(
    @SerializedName("id")
    val id: Long,

    @SerializedName("challenge_id")
    val challengeId: Long,

    @SerializedName("daedline")
    val deadline: String,

    @SerializedName("member_id")
    val memberId: Long,

    @SerializedName("study_id")
    val studyId: Long,

    @SerializedName("created_at")
    val createdAt: String,

    @SerializedName("updated_at")
    val updatedAt: String,

    @SerializedName("title")
    val title: String,
)
