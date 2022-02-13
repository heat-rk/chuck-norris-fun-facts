package ru.heatalways.chucknorrisfunfacts.core.di.modules

import dagger.Binds
import dagger.Module
import ru.heatalways.chucknorrisfunfacts.data.repositories.chuck_norris_jokes.data.repositories.chuck_norris_jokes.ChuckNorrisJokesRepositoryFake
import ru.heatalways.chucknorrisfunfacts.core_chuck_norris_jokes.domain.repositories.chuck_norris_jokes.ChuckNorrisJokesRepository

@Module(includes = [ApiModule::class])
abstract class ChuckNorrisJokesModuleFake {
    @Binds
    abstract fun bindChuckNorrisJokesRepositoryFake(
        repositoryImpl: ChuckNorrisJokesRepositoryFake
    ): ChuckNorrisJokesRepository
}