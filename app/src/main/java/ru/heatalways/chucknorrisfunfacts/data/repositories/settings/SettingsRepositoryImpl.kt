package ru.heatalways.chucknorrisfunfacts.data.repositories.settings

import ru.heatalways.chucknorrisfunfacts.data.storage.settings.SettingsStorage
import ru.heatalways.chucknorrisfunfacts.domain.repositories.settings.SettingsRepository
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
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