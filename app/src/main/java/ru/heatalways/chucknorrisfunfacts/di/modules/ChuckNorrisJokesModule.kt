package ru.heatalways.chucknorrisfunfacts.di.modules

import dagger.Binds
import dagger.Module
import ru.heatalways.chucknorrisfunfacts.domain.repositories.chuck_norris_jokes.ChuckNorrisJokesRepository
import ru.heatalways.chucknorrisfunfacts.data.repositories.chuck_norris_jokes.ChuckNorrisJokesRepositoryImpl
import ru.heatalways.chucknorrisfunfacts.domain.interactors.chuck_norris_jokes.ChuckNorrisJokesInteractor
import ru.heatalways.chucknorrisfunfacts.domain.interactors.chuck_norris_jokes.ChuckNorrisJokesInteractorImpl
import javax.inject.Singleton

@Module(includes = [ApiModule::class])
abstract class ChuckNorrisJokesModule {
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