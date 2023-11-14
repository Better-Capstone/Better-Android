package com.ssu.better.entity.study

import java.util.Locale

enum class Period(val period: String) {

    EVERYDAY("매일"),
    WEEKLY("매주"),
    BIWEEKLY("격주"),
    ;

    companion object {
        fun getEnumFromValue(value: String?): Period? {
            return try {
                value?.let { Period.valueOf(it.uppercase(Locale.getDefault())) }
            } catch (e: Exception) {
                null
            }
        }
    }
}
