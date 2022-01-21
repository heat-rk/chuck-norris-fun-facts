package ru.heatalways.chucknorrisfunfacts.domain.interactors.settings

import kotlinx.coroutines.flow.Flow
import ru.heatalways.chucknorrisfunfacts.core.models.AppSettings

interface SettingsInteractor {
    val isNightModeEnabled: Flow<Boolean>

    val isClearCacheAfterExitEnabled: Flow<Boolean>

    val settings: Flow<AppSettings>

    suspend fun saveNightModeEnabled(isEnabled: Boolean)

    suspend fun saveClearCacheAfterExitEnabled(isEnabled: Boolean)
}