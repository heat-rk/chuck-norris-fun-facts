package ru.heatalways.chucknorrisfunfacts.core_chuck_norris_jokes.di

import dagger.Component
import ru.heatalways.chucknorrisfunfacts.core_chuck_norris_jokes.di.modules.ChuckNorrisJokesApiModule
import ru.heatalways.chucknorrisfunfacts.core_chuck_norris_jokes.di.modules.ChuckNorrisJokesModule
import javax.inject.Singleton

@Component(
    dependencies = [ChuckNorrisJokesCoreDependencies::class],
    modules = [ChuckNorrisJokesModule::class, ChuckNorrisJokesApiModule::class]
)
@Singleton
internal interface ChuckNorrisJokesCoreComponent: ChuckNorrisJokesCoreApi {
    companion object {
        fun initAndGet(
            dependencies: ChuckNorrisJokesCoreDependencies
        ): ChuckNorrisJokesCoreComponent  =
            DaggerChuckNorrisJokesCoreComponent.builder()
                .chuckNorrisJokesCoreDependencies(dependencies)
                .build()
    }
}