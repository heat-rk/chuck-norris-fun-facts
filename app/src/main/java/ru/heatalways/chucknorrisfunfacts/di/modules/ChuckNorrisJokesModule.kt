package ru.heatalways.chucknorrisfunfacts.di.modules

import dagger.Module
import dagger.Provides
import ru.heatalways.chucknorrisfunfacts.domain.network.api.ChuckNorrisJokesApi
import ru.heatalways.chucknorrisfunfacts.domain.managers.chuck_norris_jokes.ChuckNorrisJokesManager
import ru.heatalways.chucknorrisfunfacts.domain.managers.chuck_norris_jokes.ChuckNorrisJokesManagerImpl
import javax.inject.Singleton

@Module(includes = [ApiModule::class])
class ChuckNorrisJokesModule {
    @Provides
    @Singleton
    fun provideChuckNorrisJokesService(api: ChuckNorrisJokesApi): ChuckNorrisJokesManager {
        return ChuckNorrisJokesManagerImpl(api)
    }
}