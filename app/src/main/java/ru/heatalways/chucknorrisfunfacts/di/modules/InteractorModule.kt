package ru.heatalways.chucknorrisfunfacts.di.modules

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.heatalways.chucknorrisfunfacts.domain.interactors.chuck_norris_jokes.ChuckNorrisJokesInteractor
import ru.heatalways.chucknorrisfunfacts.domain.interactors.chuck_norris_jokes.ChuckNorrisJokesInteractorImpl
import javax.inject.Singleton

@Module(includes = [ChuckNorrisJokesModule::class])
@InstallIn(SingletonComponent::class)
abstract class InteractorModule {
    @Binds
    @Singleton
    abstract fun bindChuckNorrisJokesInteractor(
        interactorImpl: ChuckNorrisJokesInteractorImpl
    ): ChuckNorrisJokesInteractor
}