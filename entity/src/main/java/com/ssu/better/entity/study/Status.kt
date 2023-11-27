package com.ssu.better.entity.study

import com.google.gson.annotations.SerializedName

enum class Status {
    @SerializedName("INPROGRESS")
    INPROGRESS,

    @SerializedName("END")
    END,
}
