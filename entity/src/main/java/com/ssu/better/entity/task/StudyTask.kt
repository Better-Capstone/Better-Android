package com.ssu.better.entity.task

import com.google.gson.annotations.SerializedName
import com.ssu.better.entity.challenge.Challenge
import com.ssu.better.entity.member.Member
import com.ssu.better.entity.study.SimpleStudy
import com.ssu.better.entity.user.ScoreUser
import com.ssu.better.entity.user.UserRankHistory

data class StudyTask(
    @SerializedName("study")
    val study: SimpleStudy,

    @SerializedName("user")
    val user: ScoreUser,

    @SerializedName("id")
    val taskId: Long,

    @SerializedName("title")
    val title: String,

    @SerializedName("member")
    val member: Member,

    @SerializedName("taskGroup")
    val taskGroup: TaskGroup,

    @SerializedName("challenge")
    val challenge: Challenge?,

    @SerializedName("userRankHistory")
    val userRankHistory: ArrayList<UserRankHistory>,

    @SerializedName("createdAt")
    val createdAt: String,

    @SerializedName("updatedAt")
    val updatedAt: String,
)
