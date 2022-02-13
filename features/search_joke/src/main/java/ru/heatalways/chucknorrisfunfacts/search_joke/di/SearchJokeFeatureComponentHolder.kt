package ru.heatalways.chucknorrisfunfacts.search_joke.di

import ru.heatalways.chucknorrisfunfacts.module_injector.di.ComponentHolder

object SearchJokeFeatureComponentHolder: ComponentHolder<
        SearchJokeFeatureApi, SearchJokeFeatureDependencies
        > {

    private var component: SearchJokeFeatureComponent? = null

    override fun init(dependencies: SearchJokeFeatureDependencies) {
        if (component == null) {
            synchronized(SearchJokeFeatureComponentHolder::class.java) {
                if (component == null) {
                    component = SearchJokeFeatureComponent.initAndGet(dependencies)
                }
            }
        }
    }

    override fun get(): SearchJokeFeatureApi = getComponent()

    internal fun getComponent(): SearchJokeFeatureComponent {
        checkNotNull(component) { "SearchJokeFeatureComponent was not initialized!" }
        return component!!
    }

    override fun reset() {
        component = null
    }
}