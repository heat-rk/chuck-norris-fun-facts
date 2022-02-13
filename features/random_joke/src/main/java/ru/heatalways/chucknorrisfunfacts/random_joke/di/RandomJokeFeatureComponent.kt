package ru.heatalways.chucknorrisfunfacts.random_joke.di

import dagger.Component
import ru.heatalways.chucknorrisfunfacts.common.di.PerFeature
import ru.heatalways.chucknorrisfunfacts.random_joke.presentation.screen.random_joke.RandomJokeFragment
import ru.heatalways.chucknorrisfunfacts.random_joke.presentation.screen.random_joke.select_category.CategorySelectionFragment

@Component(
    dependencies = [RandomJokeFeatureDependencies::class]
)
@PerFeature
internal interface RandomJokeFeatureComponent: RandomJokeFeatureApi {
    fun inject(fragment: RandomJokeFragment)
    fun inject(fragment: CategorySelectionFragment)

    companion object {
        fun initAndGet(dependencies: RandomJokeFeatureDependencies): RandomJokeFeatureComponent =
            DaggerRandomJokeFeatureComponent.builder()
                .randomJokeFeatureDependencies(dependencies)
                .build()
    }
}