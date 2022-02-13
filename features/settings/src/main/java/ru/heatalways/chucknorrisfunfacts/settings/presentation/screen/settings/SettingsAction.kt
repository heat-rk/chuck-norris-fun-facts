package ru.heatalways.chucknorrisfunfacts.settings.presentation.screen.settings

import ru.heatalways.chucknorrisfunfacts.core.presentation.base.MviAction

internal sealed class SettingsAction: MviAction {
    data class OnNightModeCheckedChange(val isChecked: Boolean): SettingsAction()
    data class OnClearCacheAfterExitCheckedChange(val isChecked: Boolean): SettingsAction()
}