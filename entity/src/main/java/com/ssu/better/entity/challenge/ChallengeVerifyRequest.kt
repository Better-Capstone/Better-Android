package com.ssu.better.entity.challenge

import com.google.gson.annotations.SerializedName

data class ChallengeVerifyRequest(
    @SerializedName("approved")
    val approved: Boolean,
)
