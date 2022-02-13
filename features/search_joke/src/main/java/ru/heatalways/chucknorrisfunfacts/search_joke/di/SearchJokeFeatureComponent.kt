package ru.heatalways.chucknorrisfunfacts.search_joke.di

import dagger.Component
import ru.heatalways.chucknorrisfunfacts.common.di.PerFeature
import ru.heatalways.chucknorrisfunfacts.search_joke.presentation.screen.search_joke.SearchJokeFragment

@Component(
    dependencies = [SearchJokeFeatureDependencies::class]
)
@PerFeature
internal interface SearchJokeFeatureComponent: SearchJokeFeatureApi {
    fun inject(fragment: SearchJokeFragment)

    companion object {
        fun initAndGet(dependencies: SearchJokeFeatureDependencies): SearchJokeFeatureComponent =
            DaggerSearchJokeFeatureComponent.builder()
                .searchJokeFeatureDependencies(dependencies)
                .build()
    }
}