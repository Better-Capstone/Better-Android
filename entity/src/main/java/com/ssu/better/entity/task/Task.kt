package com.ssu.better.entity.task

import com.google.gson.annotations.SerializedName
import com.ssu.better.entity.challenge.Challenge
import com.ssu.better.entity.member.Member

data class Task(
    @SerializedName("id")
    val taskId: Long,

    @SerializedName("taskGroup")
    val taskGroup: TaskGroup,

    @SerializedName("member")
    val member: Member,

    @SerializedName("title")
    val title: String,

    @SerializedName("challenge")
    val challenge: Challenge?,

    @SerializedName("createdAt")
    val createdAt: String,

    @SerializedName("updatedAt")
    val updatedAt: String,

)
