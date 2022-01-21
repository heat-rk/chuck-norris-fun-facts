package ru.heatalways.chucknorrisfunfacts.presentation.screen.settings

import ru.heatalways.chucknorrisfunfacts.core.base.MviState
import ru.heatalways.chucknorrisfunfacts.presentation.util.CompoundButtonState

data class SettingsViewState(
    val isLoading: Boolean = true,

    val nightModeSwitchState: CompoundButtonState =
        CompoundButtonState(false),

    val clearCacheAfterExitSwitchState: CompoundButtonState =
        CompoundButtonState(false)
): MviState