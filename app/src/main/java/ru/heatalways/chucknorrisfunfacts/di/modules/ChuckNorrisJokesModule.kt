package ru.heatalways.chucknorrisfunfacts.di.modules

import dagger.Module
import dagger.Provides
import ru.heatalways.chucknorrisfunfacts.domain.network.api.ChuckNorrisJokesApi
import ru.heatalways.chucknorrisfunfacts.domain.services.chuck_norris_jokes.ChuckNorrisJokesService
import ru.heatalways.chucknorrisfunfacts.domain.services.chuck_norris_jokes.ChuckNorrisJokesServiceImpl
import javax.inject.Singleton

@Module(includes = [ApiModule::class])
class ChuckNorrisJokesModule {
    @Provides
    @Singleton
    fun provideChuckNorrisJokesService(api: ChuckNorrisJokesApi): ChuckNorrisJokesService {
        return ChuckNorrisJokesServiceImpl(api)
    }
}