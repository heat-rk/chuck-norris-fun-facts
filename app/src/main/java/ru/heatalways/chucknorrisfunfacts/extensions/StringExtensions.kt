package ru.heatalways.chucknorrisfunfacts.extensions

import ru.heatalways.chucknorrisfunfacts.business.domain.utils.StringResource

fun String?.toTextResource() = StringResource.ByString(this)