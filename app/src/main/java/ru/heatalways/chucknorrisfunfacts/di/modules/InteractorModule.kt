package ru.heatalways.chucknorrisfunfacts.di.modules

import dagger.Binds
import dagger.Module
import ru.heatalways.chucknorrisfunfacts.domain.interactors.chuck_norris_jokes.ChuckNorrisJokesInteractor
import ru.heatalways.chucknorrisfunfacts.domain.interactors.chuck_norris_jokes.ChuckNorrisJokesInteractorImpl
import javax.inject.Singleton

@Module
abstract class InteractorModule {
    @Binds
    @Singleton
    abstract fun bindChuckNorrisJokesInteractor(
        interactorImpl: ChuckNorrisJokesInteractorImpl
    ): ChuckNorrisJokesInteractor
}