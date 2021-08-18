package ru.heatalways.chucknorrisfunfacts.di.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.heatalways.chucknorrisfunfacts.business.interactors.random_joke.RandomJokeInteractor
import ru.heatalways.chucknorrisfunfacts.business.interactors.random_joke.RandomJokeInteractorImpl
import ru.heatalways.chucknorrisfunfacts.business.interactors.random_joke.select_category.CategorySelectionInteractor
import ru.heatalways.chucknorrisfunfacts.business.interactors.random_joke.select_category.CategorySelectionInteractorImpl
import ru.heatalways.chucknorrisfunfacts.business.interactors.search_joke.SearchJokeInteractor
import ru.heatalways.chucknorrisfunfacts.business.interactors.search_joke.SearchJokeInteractorImpl
import ru.heatalways.chucknorrisfunfacts.business.domain.repositories.chuck_norris_jokes.ChuckNorrisJokesRepository
import javax.inject.Singleton

@Module(includes = [ChuckNorrisJokesModule::class])
@InstallIn(SingletonComponent::class)
object InteractorModule {
    @Provides
    @Singleton
    fun provideCategorySelectionInteractor(
        jokesRepository: ChuckNorrisJokesRepository
    ): CategorySelectionInteractor =
        CategorySelectionInteractorImpl(jokesRepository)

    @Provides
    @Singleton
    fun provideRandomJokeInteractor(
        jokesRepository: ChuckNorrisJokesRepository
    ): RandomJokeInteractor =
        RandomJokeInteractorImpl(jokesRepository)

    @Provides
    @Singleton
    fun provideSearchJokeInteractor(
        jokesRepository: ChuckNorrisJokesRepository
    ): SearchJokeInteractor =
        SearchJokeInteractorImpl(jokesRepository)
}