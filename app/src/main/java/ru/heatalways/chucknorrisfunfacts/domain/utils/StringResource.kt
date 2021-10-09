package ru.heatalways.chucknorrisfunfacts.domain.utils

import androidx.annotation.StringRes

sealed class StringResource {
    data class ByString(val text: String?): StringResource()
    data class ByRes(@StringRes val text: Int): StringResource()
}

fun strRes(text: String?) = StringResource.ByString(text)
fun strRes(text: Int) = StringResource.ByRes(text)
