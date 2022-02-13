package ru.heatalways.chucknorrisfunfacts.core_chuck_norris_jokes.di.modules

import dagger.Binds
import dagger.Module
import ru.heatalways.chucknorrisfunfacts.core_chuck_norris_jokes.data.repositories.chuck_norris_jokes.ChuckNorrisJokesRepositoryImpl
import ru.heatalways.chucknorrisfunfacts.core_chuck_norris_jokes.domain.interactors.chuck_norris_jokes.ChuckNorrisJokesInteractor
import ru.heatalways.chucknorrisfunfacts.core_chuck_norris_jokes.domain.interactors.chuck_norris_jokes.ChuckNorrisJokesInteractorImpl
import ru.heatalways.chucknorrisfunfacts.core_chuck_norris_jokes.domain.repositories.chuck_norris_jokes.ChuckNorrisJokesRepository
import javax.inject.Singleton

@Module
internal abstract class ChuckNorrisJokesModule {
    @Binds
    @Singleton
    abstract fun bindChuckNorrisJokesRepository(
        repositoryImpl: ChuckNorrisJokesRepositoryImpl
    ): ChuckNorrisJokesRepository

    @Binds
    @Singleton
    abstract fun bindChuckNorrisJokesInteractor(
        interactorImpl: ChuckNorrisJokesInteractorImpl
    ): ChuckNorrisJokesInteractor
}