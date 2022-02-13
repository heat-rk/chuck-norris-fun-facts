package ru.heatalways.chucknorrisfunfacts.common.presentation.utils

import ru.heatalways.chucknorrisfunfacts.common.general.utils.StringResource

sealed class ToastState {
    object Hidden: ToastState()
    data class Shown(val message: StringResource): ToastState()
}
