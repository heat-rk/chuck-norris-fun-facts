package ru.heatalways.chucknorrisfunfacts.domain.utils

import ru.heatalways.chucknorrisfunfacts.core.models.StringResource

sealed class InteractorEvent<out T> {
    object Loading: InteractorEvent<Nothing>()
    data class Error(val message: StringResource): InteractorEvent<Nothing>()
    data class Success<out T>(val body: T): InteractorEvent<T>()
}
