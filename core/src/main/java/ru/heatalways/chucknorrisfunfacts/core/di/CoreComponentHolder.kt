package ru.heatalways.chucknorrisfunfacts.core.di

import ru.heatalways.chucknorrisfunfacts.module_injector.di.ComponentHolder

object CoreComponentHolder: ComponentHolder<
        CoreApi, CoreDependencies
>{
    private var component: CoreComponent? = null

    override fun init(dependencies: CoreDependencies) {
        if (component == null) {
            synchronized(CoreComponentHolder::class) {
                if (component == null) {
                    component = CoreComponent.initAndGet(dependencies)
                }
            }
        }
    }

    override fun get(): CoreApi = getComponent()

    internal fun getComponent(): CoreComponent {
        checkNotNull(component) { "CoreComponentHolder was not initialized!" }
        return component!!
    }

    override fun reset() {
        component = null
    }
}