package ru.heatalways.chucknorrisfunfacts.domain.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    fun toDate(timestamp: String, format: Format): Date? {
        val dateFormatter = SimpleDateFormat(format.template, Locale.getDefault())
        return dateFormatter.parse(timestamp)
    }

    fun toMillis(timestamp: String, format: Format): Long? {
        val dateFormatter = SimpleDateFormat(format.template, Locale.getDefault())
        return dateFormatter.parse(timestamp)?.time
    }

    enum class Format(val template: String) {
        Default("yyyy-MM-dd hh:mm:ss.ssssss")
    }
}