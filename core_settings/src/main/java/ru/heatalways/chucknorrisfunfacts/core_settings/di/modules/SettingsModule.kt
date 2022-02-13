package ru.heatalways.chucknorrisfunfacts.core_settings.di.modules

import dagger.Binds
import dagger.Module
import ru.heatalways.chucknorrisfunfacts.core_settings.data.repositories.settings.SettingsRepositoryImpl
import ru.heatalways.chucknorrisfunfacts.core_settings.data.storage.settings.PreferencesSettingsStorage
import ru.heatalways.chucknorrisfunfacts.core_settings.data.storage.settings.SettingsStorage
import ru.heatalways.chucknorrisfunfacts.core_settings.domain.interactors.settings.SettingsInteractor
import ru.heatalways.chucknorrisfunfacts.core_settings.domain.interactors.settings.SettingsInteractorImpl
import ru.heatalways.chucknorrisfunfacts.core_settings.domain.repositories.settings.SettingsRepository
import javax.inject.Singleton

@Module
internal abstract class SettingsModule {
    @Binds
    @Singleton
    abstract fun bindSettingsStorage(impl: PreferencesSettingsStorage): SettingsStorage

    @Binds
    @Singleton
    abstract fun bindSettingsRepository(impl: SettingsRepositoryImpl): SettingsRepository

    @Binds
    @Singleton
    abstract fun bindSettingsInteractor(impl: SettingsInteractorImpl): SettingsInteractor
}