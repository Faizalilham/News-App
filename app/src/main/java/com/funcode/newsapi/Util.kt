package com.funcode.newsapi

import org.ocpsoft.prettytime.PrettyTime
import java.text.SimpleDateFormat
import java.util.*
import kotlin.Exception

object Util{

        val url = "url"
        fun setDateTimeHourAgo(dateTime: String?): String? {
            val prettyTime = PrettyTime(Locale.getDefault())
            var isTime: String? = null
            try {
                val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
                val date = dateTime?.let { simpleDateFormat.parse(it) }
                isTime = prettyTime.format(date)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return isTime
        }

        fun setDateFormat(dateNews: String?): String? {
            val isDate: String?
            val dateFormat = SimpleDateFormat("MMMM dd, yyyy - HH:mm:ss", Locale(getCountry()))
            isDate = try {
                val date = dateNews?.let { SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(it) }
                date?.let { dateFormat.format(it) }
            } catch (e: Exception) {
                e.printStackTrace()
                dateNews
            }
            return isDate
        }

        fun getCountry(): String {
            val locale = Locale.getDefault()
            val strCountry = locale.country
            return strCountry.toLowerCase(Locale.ROOT)
        }
}