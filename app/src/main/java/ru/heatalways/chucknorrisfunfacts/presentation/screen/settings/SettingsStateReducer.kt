package ru.heatalways.chucknorrisfunfacts.presentation.screen.settings

import ru.heatalways.chucknorrisfunfacts.core.base.MviReducer

object SettingsStateReducer: MviReducer<SettingsViewState, SettingsPartialState> {
    override fun reduce(
        state: SettingsViewState,
        partialState: SettingsPartialState
    ) = when (partialState) {
        is SettingsPartialState.ClearCacheAfterExitSwitchState -> state.copy(
            clearCacheAfterExitSwitchState = partialState.state
        )

        is SettingsPartialState.NightModeSwitchState -> state.copy(
            nightModeSwitchState = partialState.state
        )

        is SettingsPartialState.LoadingState -> state.copy(
            isLoading = partialState.isLoading
        )
    }
}