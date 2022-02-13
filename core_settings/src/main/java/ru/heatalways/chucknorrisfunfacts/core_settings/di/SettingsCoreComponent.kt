package ru.heatalways.chucknorrisfunfacts.core_settings.di

import dagger.Component
import ru.heatalways.chucknorrisfunfacts.core_settings.di.modules.SettingsModule
import javax.inject.Singleton

@Component(
    dependencies = [SettingsCoreDependencies::class],
    modules = [SettingsModule::class]
)
@Singleton
internal interface SettingsCoreComponent: SettingsCoreApi {
    companion object {
        fun initAndGet(dependencies: SettingsCoreDependencies): SettingsCoreComponent =
            DaggerSettingsCoreComponent.builder()
                .settingsCoreDependencies(dependencies)
                .build()
    }
}