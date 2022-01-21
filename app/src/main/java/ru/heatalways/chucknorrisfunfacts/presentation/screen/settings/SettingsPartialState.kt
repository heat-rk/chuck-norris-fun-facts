package ru.heatalways.chucknorrisfunfacts.presentation.screen.settings

import ru.heatalways.chucknorrisfunfacts.presentation.util.CompoundButtonState

sealed class SettingsPartialState {
    data class LoadingState(val isLoading: Boolean): SettingsPartialState()

    data class NightModeSwitchState(val state: CompoundButtonState): SettingsPartialState()

    data class ClearCacheAfterExitSwitchState(val state: CompoundButtonState): SettingsPartialState()
}