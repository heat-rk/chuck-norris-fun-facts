package ru.heatalways.chucknorrisfunfacts.presentation.screen.settings

import ru.heatalways.chucknorrisfunfacts.core.base.MviAction

sealed class SettingsAction: MviAction {
    data class OnNightModeCheckedChange(val isChecked: Boolean): SettingsAction()
    data class OnClearCacheAfterExitCheckedChange(val isChecked: Boolean): SettingsAction()
}