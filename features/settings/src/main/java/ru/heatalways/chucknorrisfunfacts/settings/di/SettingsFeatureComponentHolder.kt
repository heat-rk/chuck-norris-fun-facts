package ru.heatalways.chucknorrisfunfacts.settings.di

import ru.heatalways.chucknorrisfunfacts.module_injector.di.ComponentHolder

object SettingsFeatureComponentHolder: ComponentHolder<
        SettingsFeatureApi, SettingsFeatureDependencies
        > {
    private var component: SettingsFeatureComponent? = null

    override fun init(dependencies: SettingsFeatureDependencies) {
        if (component == null) {
            synchronized(SettingsFeatureComponentHolder::class.java) {
                if (component == null) {
                    component = SettingsFeatureComponent.initAndGet(dependencies)
                }
            }
        }
    }

    override fun get(): SettingsFeatureApi = getComponent()

    internal fun getComponent(): SettingsFeatureComponent {
        checkNotNull(component) { "SettingsFeatureComponent was not initialized!" }
        return component!!
    }

    override fun reset() {
        component = null
    }
}