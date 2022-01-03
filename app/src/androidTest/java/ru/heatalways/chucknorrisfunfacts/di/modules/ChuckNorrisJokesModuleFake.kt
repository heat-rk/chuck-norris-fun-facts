package ru.heatalways.chucknorrisfunfacts.di.modules

import dagger.Binds
import dagger.Module
import ru.heatalways.chucknorrisfunfacts.data.repositories.chuck_norris_jokes.ChuckNorrisJokesRepositoryFake
import ru.heatalways.chucknorrisfunfacts.domain.repositories.chuck_norris_jokes.ChuckNorrisJokesRepository

@Module(includes = [ApiModule::class])
abstract class ChuckNorrisJokesModuleFake {
    @Binds
    abstract fun bindChuckNorrisJokesRepositoryFake(
        repositoryImpl: ChuckNorrisJokesRepositoryFake
    ): ChuckNorrisJokesRepository
}