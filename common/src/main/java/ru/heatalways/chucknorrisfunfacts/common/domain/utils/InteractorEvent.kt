package ru.heatalways.chucknorrisfunfacts.common.domain.utils

import ru.heatalways.chucknorrisfunfacts.common.general.utils.StringResource

sealed class InteractorEvent<out T> {
    object Loading: InteractorEvent<Nothing>()
    data class Error(val message: StringResource): InteractorEvent<Nothing>()
    data class Success<out T>(val body: T): InteractorEvent<T>()
}
