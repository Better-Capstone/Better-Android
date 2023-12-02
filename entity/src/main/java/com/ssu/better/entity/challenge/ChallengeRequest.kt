package com.ssu.better.entity.challenge

import com.google.gson.annotations.SerializedName

data class ChallengeRequest(
    @SerializedName("title")
    val title: String,

    @SerializedName("description")
    val description: String,
)
