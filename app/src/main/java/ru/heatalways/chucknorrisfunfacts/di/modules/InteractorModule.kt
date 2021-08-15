package ru.heatalways.chucknorrisfunfacts.di.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.heatalways.chucknorrisfunfacts.domain.interactors.random_joke.RandomJokeInteractor
import ru.heatalways.chucknorrisfunfacts.domain.interactors.random_joke.RandomJokeInteractorImpl
import ru.heatalways.chucknorrisfunfacts.domain.interactors.random_joke.select_category.CategorySelectionInteractor
import ru.heatalways.chucknorrisfunfacts.domain.interactors.random_joke.select_category.CategorySelectionInteractorImpl
import ru.heatalways.chucknorrisfunfacts.domain.interactors.search_joke.SearchJokeInteractor
import ru.heatalways.chucknorrisfunfacts.domain.interactors.search_joke.SearchJokeInteractorImpl
import ru.heatalways.chucknorrisfunfacts.domain.managers.chuck_norris_jokes.ChuckNorrisJokesManager
import javax.inject.Singleton

@Module(includes = [ChuckNorrisJokesModule::class])
@InstallIn(SingletonComponent::class)
object InteractorModule {
    @Provides
    @Singleton
    fun provideCategorySelectionModule(
        jokesManager: ChuckNorrisJokesManager
    ): CategorySelectionInteractor =
        CategorySelectionInteractorImpl(jokesManager)

    @Provides
    @Singleton
    fun provideRandomJokeInteractor(
        jokesManager: ChuckNorrisJokesManager
    ): RandomJokeInteractor =
        RandomJokeInteractorImpl(jokesManager)

    @Provides
    @Singleton
    fun provideSearchJokeInteractor(
        jokesManager: ChuckNorrisJokesManager
    ): SearchJokeInteractor =
        SearchJokeInteractorImpl(jokesManager)
}