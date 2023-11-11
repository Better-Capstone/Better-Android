package com.ssu.better.entity.study

import com.google.gson.annotations.SerializedName

data class StudyCategory(
    @SerializedName("id")
    val categoryId: Long,

    @SerializedName("name")
    val name: String,
)
