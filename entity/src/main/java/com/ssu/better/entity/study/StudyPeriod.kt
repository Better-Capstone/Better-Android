package com.ssu.better.entity.study

import com.google.gson.annotations.SerializedName

enum class StudyPeriod(val kor: String) {
    @SerializedName("EVERYDAY")
    EVERYDAY("매일"),

    @SerializedName("WEEKLY")
    WEEKLY("매주"),

    @SerializedName("BIWEEKLY")
    BIWEEKLY("격주"),
}
