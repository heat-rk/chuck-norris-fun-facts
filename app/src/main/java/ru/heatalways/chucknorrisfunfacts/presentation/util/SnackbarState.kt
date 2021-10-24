package ru.heatalways.chucknorrisfunfacts.presentation.util

import ru.heatalways.chucknorrisfunfacts.core.utils.StringResource

sealed class SnackbarState {
    object Hidden: SnackbarState()
    data class Shown(
        val message: StringResource,
        val buttonText: StringResource? = null,
        val buttonCallback: () -> Unit = {},
        val duration: Long = LONG_DURATION
    ): SnackbarState()

    companion object {
        const val SHORT_DURATION = 2000L
        const val LONG_DURATION = 5000L
    }
}
