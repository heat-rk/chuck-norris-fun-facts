package ru.heatalways.chucknorrisfunfacts.common.extensions

import android.content.Context
import ru.heatalways.chucknorrisfunfacts.common.general.utils.StringResource

fun String?.toTextResource() = StringResource.ByString(this)

fun StringResource?.toCharSequence(context: Context) = when (this) {
    is StringResource.ByRes -> context.getString(text)
    is StringResource.ByString -> text
    else -> null
}