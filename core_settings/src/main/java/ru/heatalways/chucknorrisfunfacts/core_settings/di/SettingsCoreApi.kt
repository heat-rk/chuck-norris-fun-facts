package ru.heatalways.chucknorrisfunfacts.core_settings.di

import ru.heatalways.chucknorrisfunfacts.core_settings.domain.interactors.settings.SettingsInteractor
import ru.heatalways.chucknorrisfunfacts.core_settings.domain.repositories.settings.SettingsRepository
import ru.heatalways.chucknorrisfunfacts.module_injector.di.BaseModuleApi

interface SettingsCoreApi: BaseModuleApi {
    fun getSettingsRepository(): SettingsRepository
    fun getSettingsInteractor(): SettingsInteractor
}