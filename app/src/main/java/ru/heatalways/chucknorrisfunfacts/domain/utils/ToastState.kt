package ru.heatalways.chucknorrisfunfacts.domain.utils

sealed class ToastState {
    object Hidden: ToastState()
    data class Shown(val message: StringResource): ToastState()
}
