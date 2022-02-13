package ru.heatalways.chucknorrisfunfacts.core_chuck_norris_jokes.di

import ru.heatalways.chucknorrisfunfacts.module_injector.di.ComponentHolder

object ChuckNorrisJokesCoreComponentHolder: ComponentHolder<
    ChuckNorrisJokesCoreApi, ChuckNorrisJokesCoreDependencies
> {
    private var component: ChuckNorrisJokesCoreComponent? = null

    override fun init(dependencies: ChuckNorrisJokesCoreDependencies) {
        if (component == null) {
            synchronized(ChuckNorrisJokesCoreComponentHolder::class) {
                if (component == null) {
                    component = ChuckNorrisJokesCoreComponent.initAndGet(dependencies)
                }
            }
        }
    }

    override fun get(): ChuckNorrisJokesCoreApi = getComponent()

    internal fun getComponent(): ChuckNorrisJokesCoreComponent {
        checkNotNull(component) { "ChuckNorrisJokesCoreComponent was not initialized!" }
        return component!!
    }

    override fun reset() {
        component = null
    }
}