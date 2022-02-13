package ru.heatalways.chucknorrisfunfacts.core_settings.data.storage.settings

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import ru.heatalways.chucknorrisfunfacts.core.general.AppSettings
import ru.heatalways.chucknorrisfunfacts.common.extensions.safeDataMap
import javax.inject.Inject

internal class PreferencesSettingsStorage @Inject constructor(
    private val context: Context
): SettingsStorage {

    private val Context.dataStore by preferencesDataStore(
        name = SETTINGS_PREFERENCES_DATA_STORE_NAME
    )

    override val isNightModeEnabled
        get() = context.dataStore.safeDataMap { it[NIGHT_MODE_ENABLED_KEY] ?: false }

    override val isClearCacheAfterExitEnabled
        get() = context.dataStore.safeDataMap { it[CLEAR_CACHE_AFTER_EXIT_ENABLED_KEY] ?: false }

    override val settings
        get() = context.dataStore.safeDataMap {
            AppSettings(
                isNightModeEnabled = it[NIGHT_MODE_ENABLED_KEY] ?: false,
                isClearCacheAfterExitEnabled = it[CLEAR_CACHE_AFTER_EXIT_ENABLED_KEY] ?: false
            )
        }

    override suspend fun saveNightModeEnabled(isEnabled: Boolean) {
        context.dataStore.edit { it[NIGHT_MODE_ENABLED_KEY] = isEnabled }
    }

    override suspend fun saveClearCacheAfterExitEnabled(isEnabled: Boolean) {
        context.dataStore.edit { it[CLEAR_CACHE_AFTER_EXIT_ENABLED_KEY] = isEnabled }
    }

    companion object {
        private const val SETTINGS_PREFERENCES_DATA_STORE_NAME =
            "settings_preferences_data_store_name"

        private val NIGHT_MODE_ENABLED_KEY =
            booleanPreferencesKey("settingsPreferences.nightModeEnabledKey")

        private val CLEAR_CACHE_AFTER_EXIT_ENABLED_KEY =
            booleanPreferencesKey("settingsPreferences.clearCacheAfterExitKey")
    }
}