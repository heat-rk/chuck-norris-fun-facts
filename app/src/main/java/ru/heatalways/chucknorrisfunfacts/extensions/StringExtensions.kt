package ru.heatalways.chucknorrisfunfacts.extensions

import android.content.Context
import ru.heatalways.chucknorrisfunfacts.core.models.StringResource

fun String?.toTextResource() = StringResource.ByString(this)

fun StringResource?.toCharSequence(context: Context) = when (this) {
    is StringResource.ByRes -> context.getString(text)
    is StringResource.ByString -> text
    else -> null
}