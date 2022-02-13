package ru.heatalways.chucknorrisfunfacts.core_settings.di

import ru.heatalways.chucknorrisfunfacts.module_injector.di.ComponentHolder

object SettingsCoreComponentHolder: ComponentHolder<
        SettingsCoreApi, SettingsCoreDependencies
> {
    private var component: SettingsCoreComponent? = null

    override fun init(dependencies: SettingsCoreDependencies) {
        if (component == null) {
            synchronized(SettingsCoreComponentHolder::class) {
                if (component == null) {
                    component = SettingsCoreComponent.initAndGet(dependencies)
                }
            }
        }
    }

    override fun get(): SettingsCoreApi = getComponent()

    internal fun getComponent(): SettingsCoreComponent {
        checkNotNull(component) { "SettingsCoreComponent was not initialized!" }
        return component!!
    }

    override fun reset() {
        component = null
    }
}