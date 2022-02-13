package ru.heatalways.chucknorrisfunfacts.settings.presentation.screen.settings

import ru.heatalways.chucknorrisfunfacts.core.presentation.base.MviState
import ru.heatalways.chucknorrisfunfacts.common.presentation.utils.CompoundButtonState

internal data class SettingsViewState(
    val isLoading: Boolean = true,

    val nightModeSwitchState: CompoundButtonState =
        CompoundButtonState(false),

    val clearCacheAfterExitSwitchState: CompoundButtonState =
        CompoundButtonState(false)
): MviState