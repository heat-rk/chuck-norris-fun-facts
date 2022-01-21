package ru.heatalways.chucknorrisfunfacts.domain.interactors.settings

import ru.heatalways.chucknorrisfunfacts.domain.repositories.settings.SettingsRepository
import javax.inject.Inject

class SettingsInteractorImpl @Inject constructor(
    private val settingsRepository: SettingsRepository
): SettingsInteractor {
    override val isNightModeEnabled
        get() = settingsRepository.isNightModeEnabled

    override val isClearCacheAfterExitEnabled
        get() = settingsRepository.isClearCacheAfterExitEnabled

    override val settings
        get() = settingsRepository.settings

    override suspend fun saveNightModeEnabled(isEnabled: Boolean) {
        settingsRepository.saveNightModeEnabled(isEnabled)
    }

    override suspend fun saveClearCacheAfterExitEnabled(isEnabled: Boolean) {
        settingsRepository.saveClearCacheAfterExitEnabled(isEnabled)
    }
}