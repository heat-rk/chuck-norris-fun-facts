package ru.heatalways.chucknorrisfunfacts.settings.di

import ru.heatalways.chucknorrisfunfacts.module_injector.di.BaseModuleDependencies
import ru.heatalways.chucknorrisfunfacts.core_settings.domain.interactors.settings.SettingsInteractor

interface SettingsFeatureDependencies: BaseModuleDependencies {
    fun getSettingsInteractor(): SettingsInteractor
}