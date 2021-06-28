package ru.heatalways.chucknorrisfunfacts.di.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.heatalways.chucknorrisfunfacts.domain.database.AppDatabase
import ru.heatalways.chucknorrisfunfacts.domain.managers.chuck_norris_jokes.ChuckNorrisJokesManager
import ru.heatalways.chucknorrisfunfacts.domain.managers.chuck_norris_jokes.ChuckNorrisJokesManagerImpl
import ru.heatalways.chucknorrisfunfacts.domain.network.api.ChuckNorrisJokesApi
import javax.inject.Singleton

@Module(includes = [ApiModule::class])
@InstallIn(SingletonComponent::class)
class ChuckNorrisJokesModule {
    @Provides
    @Singleton
    fun provideChuckNorrisJokesService(
        api: ChuckNorrisJokesApi,
        database: AppDatabase
    ): ChuckNorrisJokesManager {
        return ChuckNorrisJokesManagerImpl(api, database)
    }
}