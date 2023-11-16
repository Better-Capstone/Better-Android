package com.ssu.better.entity.study

import com.google.gson.annotations.SerializedName
import java.time.DayOfWeek
import java.util.Locale

enum class CheckDay(val checkDay: String) {
    @SerializedName("EVERYDAY")
    EVERYDAY("Everyday"),

    @SerializedName("MON")
    MON("Monday"),

    @SerializedName("TUE")
    TUE("Tuesday"),

    @SerializedName("WED")
    WED("Wednesday"),

    @SerializedName("THU")
    THU("Thursday"),

    @SerializedName("FRI")
    FRI("Friday"),

    @SerializedName("SAT")
    SAT("Saturday"),

    @SerializedName("SUN")
    SUN("Sunday"),
    ;

    companion object {
        fun CheckDay.getUpperName(): String {
            return this.checkDay.uppercase(Locale.getDefault())
        }

        fun CheckDay.convertToDayOfWeek(): DayOfWeek? {
            if (this == EVERYDAY) return null
            return DayOfWeek.valueOf(this.getUpperName())
        }
    }
}
