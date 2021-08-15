package ru.heatalways.chucknorrisfunfacts.data.utils

import android.content.Context
import androidx.annotation.StringRes

sealed class StringResource {
    data class ByString(val text: String?): StringResource()
    data class ByRes(@StringRes val text: Int): StringResource()

    fun getText(context: Context) = when (this) {
        is ByString -> text
        is ByRes -> context.getString(text)
    }
}

fun strRes(text: String?) = StringResource.ByString(text)
fun strRes(text: Int) = StringResource.ByRes(text)
