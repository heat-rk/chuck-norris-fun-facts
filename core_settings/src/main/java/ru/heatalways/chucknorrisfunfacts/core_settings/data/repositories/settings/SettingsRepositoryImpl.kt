package ru.heatalways.chucknorrisfunfacts.core_settings.data.repositories.settings

import ru.heatalways.chucknorrisfunfacts.core_settings.data.storage.settings.SettingsStorage
import ru.heatalways.chucknorrisfunfacts.core_settings.domain.repositories.settings.SettingsRepository
import javax.inject.Inject

internal class SettingsRepositoryImpl @Inject constructor(
    private val settingsStorage: SettingsStorage
): SettingsRepository {
    override val isNightModeEnabled
        get() = settingsStorage.isNightModeEnabled

    override val isClearCacheAfterExitEnabled
        get() = settingsStorage.isClearCacheAfterExitEnabled

    override val settings
        get() = settingsStorage.settings

    override suspend fun saveNightModeEnabled(isEnabled: Boolean) {
        settingsStorage.saveNightModeEnabled(isEnabled)
    }

    override suspend fun saveClearCacheAfterExitEnabled(isEnabled: Boolean) {
        settingsStorage.saveClearCacheAfterExitEnabled(isEnabled)
    }
}