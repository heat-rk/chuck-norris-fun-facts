package ru.heatalways.chucknorrisfunfacts.settings.di

import dagger.Component
import ru.heatalways.chucknorrisfunfacts.common.di.PerFeature
import ru.heatalways.chucknorrisfunfacts.settings.presentation.screen.settings.SettingsFragment

@Component(
    dependencies = [SettingsFeatureDependencies::class]
)
@PerFeature
internal interface SettingsFeatureComponent: SettingsFeatureApi {
    fun inject(fragment: SettingsFragment)

    companion object {
        fun initAndGet(dependencies: SettingsFeatureDependencies): SettingsFeatureComponent =
            DaggerSettingsFeatureComponent.builder()
                .settingsFeatureDependencies(dependencies)
                .build()
    }
}