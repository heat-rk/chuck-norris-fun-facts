package ru.heatalways.chucknorrisfunfacts.settings.presentation.screen.settings

import ru.heatalways.chucknorrisfunfacts.common.presentation.utils.CompoundButtonState

internal sealed class SettingsPartialState {
    data class LoadingState(val isLoading: Boolean): SettingsPartialState()

    data class NightModeSwitchState(val state: CompoundButtonState): SettingsPartialState()

    data class ClearCacheAfterExitSwitchState(val state: CompoundButtonState): SettingsPartialState()
}