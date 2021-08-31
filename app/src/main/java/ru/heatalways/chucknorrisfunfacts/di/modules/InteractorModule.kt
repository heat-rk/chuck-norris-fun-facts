package ru.heatalways.chucknorrisfunfacts.di.modules

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.heatalways.chucknorrisfunfacts.domain.interactors.random_joke.RandomJokeInteractor
import ru.heatalways.chucknorrisfunfacts.domain.interactors.random_joke.RandomJokeInteractorImpl
import ru.heatalways.chucknorrisfunfacts.domain.interactors.random_joke.select_category.CategorySelectionInteractor
import ru.heatalways.chucknorrisfunfacts.domain.interactors.random_joke.select_category.CategorySelectionInteractorImpl
import ru.heatalways.chucknorrisfunfacts.domain.interactors.search_joke.SearchJokeInteractor
import ru.heatalways.chucknorrisfunfacts.domain.interactors.search_joke.SearchJokeInteractorImpl
import javax.inject.Singleton

@Module(includes = [ChuckNorrisJokesModule::class])
@InstallIn(SingletonComponent::class)
abstract class InteractorModule {
    @Binds
    @Singleton
    abstract fun bindCategorySelectionInteractor(
        interactorImpl: CategorySelectionInteractorImpl
    ): CategorySelectionInteractor

    @Binds
    @Singleton
    abstract fun bindRandomJokeInteractor(
        interactorImpl: RandomJokeInteractorImpl
    ): RandomJokeInteractor

    @Binds
    @Singleton
    abstract fun bindSearchJokeInteractor(
        interactorImpl: SearchJokeInteractorImpl
    ): SearchJokeInteractor
}