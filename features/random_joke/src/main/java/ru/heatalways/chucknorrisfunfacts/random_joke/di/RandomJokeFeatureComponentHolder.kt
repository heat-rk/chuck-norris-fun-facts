package ru.heatalways.chucknorrisfunfacts.random_joke.di

import ru.heatalways.chucknorrisfunfacts.module_injector.di.ComponentHolder

object RandomJokeFeatureComponentHolder: ComponentHolder<
        RandomJokeFeatureApi, RandomJokeFeatureDependencies
        > {
    private var component: RandomJokeFeatureComponent? = null

    override fun init(dependencies: RandomJokeFeatureDependencies) {
        if (component == null) {
            synchronized(RandomJokeFeatureComponentHolder::class.java) {
                if (component == null) {
                    component = RandomJokeFeatureComponent.initAndGet(dependencies)
                }
            }
        }
    }

    override fun get(): RandomJokeFeatureApi = getComponent()

    internal fun getComponent(): RandomJokeFeatureComponent {
        checkNotNull(component) { "RandomJokeFeatureComponent was not initialized!" }
        return component!!
    }

    override fun reset() {
        component = null
    }
}