package com.ssu.better.entity.study

import java.util.Locale

enum class CheckDay(val checkDay: String) {

    EVERYDAY("Everyday"),
    MON("Monday"),
    TUE("Tuesday"),
    WED("Wednesday"),
    THU("Thursday"),
    FRI("Friday"),
    SAT("Saturday"),
    SUN("Sunday"),
    ;

    companion object {
        @JvmStatic
        fun getEnumFromValue(value: String?): CheckDay? {
            return try {
                value?.let { CheckDay.valueOf(it.uppercase(Locale.getDefault())) }
            } catch (e: Exception) {
                null
            }
        }
    }
}
