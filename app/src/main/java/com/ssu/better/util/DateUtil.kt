package com.ssu.better.util

import android.annotation.SuppressLint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

@SuppressLint("SimpleDateFormat")
fun String.toCalendar(): Calendar? {
    val simpleDateFormat: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS")
    return try {
        val date: Date? = simpleDateFormat.parse(this)
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar
    } catch (e: ParseException) {
        e.printStackTrace()
        null
    }
}
