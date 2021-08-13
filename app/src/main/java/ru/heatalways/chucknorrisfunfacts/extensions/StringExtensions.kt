package ru.heatalways.chucknorrisfunfacts.extensions

import ru.heatalways.chucknorrisfunfacts.data.utils.StringResource

fun String?.toTextResource() = StringResource.ByString(this)