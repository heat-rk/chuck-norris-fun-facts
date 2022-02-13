package ru.heatalways.chucknorrisfunfacts.core_settings.domain.interactors.settings

import kotlinx.coroutines.flow.Flow
import ru.heatalways.chucknorrisfunfacts.core.general.AppSettings

interface SettingsInteractor {
    val isNightModeEnabled: Flow<Boolean>

    val isClearCacheAfterExitEnabled: Flow<Boolean>

    val settings: Flow<AppSettings>

    suspend fun saveNightModeEnabled(isEnabled: Boolean)

    suspend fun saveClearCacheAfterExitEnabled(isEnabled: Boolean)
}