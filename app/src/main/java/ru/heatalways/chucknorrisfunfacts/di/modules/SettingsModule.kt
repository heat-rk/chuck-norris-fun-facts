package ru.heatalways.chucknorrisfunfacts.di.modules

import dagger.Binds
import dagger.Module
import ru.heatalways.chucknorrisfunfacts.data.repositories.settings.SettingsRepositoryImpl
import ru.heatalways.chucknorrisfunfacts.data.storage.settings.PreferencesSettingsStorage
import ru.heatalways.chucknorrisfunfacts.data.storage.settings.SettingsStorage
import ru.heatalways.chucknorrisfunfacts.domain.interactors.settings.SettingsInteractor
import ru.heatalways.chucknorrisfunfacts.domain.interactors.settings.SettingsInteractorImpl
import ru.heatalways.chucknorrisfunfacts.domain.repositories.settings.SettingsRepository
import javax.inject.Singleton

@Module(includes = [AppModule::class])
abstract class SettingsModule {
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