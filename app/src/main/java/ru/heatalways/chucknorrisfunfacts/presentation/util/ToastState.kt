package ru.heatalways.chucknorrisfunfacts.presentation.util

import ru.heatalways.chucknorrisfunfacts.core.utils.StringResource

sealed class ToastState {
    object Hidden: ToastState()
    data class Shown(val message: StringResource): ToastState()
}
