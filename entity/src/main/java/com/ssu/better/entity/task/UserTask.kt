package com.ssu.better.entity.task

import com.google.gson.annotations.SerializedName
import com.ssu.better.entity.challenge.Challenge
import com.ssu.better.entity.member.Member
import com.ssu.better.entity.user.UserRankHistory

data class UserTask(
    @SerializedName("study")
    val study: UserTaskStudy,
    @SerializedName("id")
    val id: Long,
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

data class UserTaskStudy(
    @SerializedName("id")
    val id: Long,
    @SerializedName("title")
    val title: String,
)

fun UserTask.toTask(): Task {
    return Task(
        taskId = this.id,
        title = this.title,
        member = this.member,
        taskGroup = this.taskGroup,
        challenge = this.challenge,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt,
    )
}
