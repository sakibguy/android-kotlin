package com.sp0ort365.mawt.utils

import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

class DateUtils()  {

    fun getCurrentDate() :String {
        val timeInms = System.currentTimeMillis()
        val date = Date(timeInms)
        val result= SimpleDateFormat("EEEE, dd MMM ", Locale.getDefault()).format(date)
        return result
    }

    fun getOneMonthLater() :String {
        val timeInms = System.currentTimeMillis() + (30 *24 * 60 *60 *1000L)
        val date = Date(timeInms)
        val result= SimpleDateFormat("EEEE, dd MMM", Locale.getDefault()).format(date)
        return result
    }

    fun getOneWeekBefore() :String {
        val timeInms = System.currentTimeMillis() - (7 * 24 * 60 *60 *1000L)
        val date = Date(timeInms)
        val result= SimpleDateFormat("EEEE, dd MMM", Locale.getDefault()).format(date)
        return result
    }

    fun getCurrentMonth() :String {
        val timeInms = System.currentTimeMillis()
        val date = Date(timeInms)
        val result= SimpleDateFormat("MMM", Locale.getDefault()).format(date)
        return result
    }

    fun getCurrentYear() :String {
        val timeInms = System.currentTimeMillis()
        val date = Date(timeInms)
        val result= SimpleDateFormat("yyyy", Locale.getDefault()).format(date)
        return result
    }

    fun convertDate(date: Date, patternTo:String) :String {
        val dateFormat = SimpleDateFormat(patternTo, Locale.getDefault())
        return  dateFormat.format(date)
    }
    fun stringToDate(date:String,pattern:String) : Date {
        val formatter = SimpleDateFormat(pattern, Locale.getDefault())
        return formatter.parse(date) ?: Date()
    }

    fun dateToCalendar(date: Date) : Calendar {
        val cal = Calendar.getInstance()
        cal.time = date
        return cal
    }

    fun dateToHour(date: Date): Int{
        val cal = Calendar.getInstance()
        cal.time = date
        return cal.get(Calendar.HOUR_OF_DAY)
    }

    fun getNextTimeAsLong(day:Int) :Long {
        val timeInms = System.currentTimeMillis() + (day * 24 * 60 *60 *1000L)
        return timeInms
    }

    fun getPastTimeAsLong(day:Int) :Long {
        val timeInms = System.currentTimeMillis() - (day * 24 * 60 *60 *1000L)
        return timeInms
    }
}