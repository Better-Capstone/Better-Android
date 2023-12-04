package com.ssu.better.util

import android.annotation.SuppressLint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.time.Period
import java.time.YearMonth
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun YearMonth.dateFormat(pattern: String, locale: Locale = Locale.getDefault()): String = format(DateTimeFormatter.ofPattern(pattern, locale))

fun LocalDate.dateFormat(pattern: String, locale: Locale = Locale.getDefault()): String = format(DateTimeFormatter.ofPattern(pattern, locale))

fun LocalTime.timeFormat(pattern: String, locale: Locale = Locale.getDefault()): String = format(DateTimeFormatter.ofPattern(pattern, locale))

fun getDaysOfWeek(localDate: LocalDate): ArrayList<LocalDate> {
    val days = arrayListOf<LocalDate>()
    try {
        val systemTimeZone = ZoneId.systemDefault()
        val zonedDateTime = localDate.atStartOfDay(systemTimeZone)
        val date: Date = Date.from(zonedDateTime.toInstant())
        val cal: Calendar = Calendar.getInstance()

        cal.time = date

        for (i in 1..7) {
            cal.set(Calendar.DAY_OF_WEEK, i)
            val day = convertToLocalDateViaInstant(cal.time)
            if (day != null) days.add(day)
        }
    } catch (e: ParseException) {
    }
    return days
}

fun convertToLocalDateViaInstant(dateToConvert: Date): LocalDate? {
    return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
}

fun convertToDateViaInstant(dateToConvert: LocalDate): Date? {
    return Date.from(
        dateToConvert.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant(),
    )
}

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

fun String.toLocalDate(): LocalDate? {
    val pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    val dateFormat = DateTimeFormatter.ofPattern(pattern)
    return try {
        val localDate = LocalDate.parse(this, dateFormat)
        localDate
    } catch (e: ParseException) {
        e.printStackTrace()
        null
    }
}

fun getDDay(now: LocalDate, target: LocalDate): String {
    val period = Period.between(now, target)
    return if (period.days >= 0) {
        "D-${period.days}"
    } else {
        "D+${period.days * (-1)}"
    }
}

fun LocalDate.calendarFormatStr(): String {
    val pattern = "MM월 dd일(E)"
    return dateFormat(pattern, Locale.KOREA)
}

fun convertToLocalDateByFormat(text: String, pattern: String): LocalDate? {
    val dateFormat = DateTimeFormatter.ofPattern(pattern)
    return try {
        val localDate = LocalDate.parse(text, dateFormat)
        localDate
    } catch (e: ParseException) {
        e.printStackTrace()
        null
    }
}
