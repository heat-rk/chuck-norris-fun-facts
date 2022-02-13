package ru.heatalways.chucknorrisfunfacts.core_settings.data.storage.settings

import kotlinx.coroutines.flow.Flow
import ru.heatalways.chucknorrisfunfacts.core.general.AppSettings

interface SettingsStorage {
    val isNightModeEnabled: Flow<Boolean>

    val isClearCacheAfterExitEnabled: Flow<Boolean>

    val settings: Flow<AppSettings>

    suspend fun saveNightModeEnabled(isEnabled: Boolean)

    suspend fun saveClearCacheAfterExitEnabled(isEnabled: Boolean)
}