package ru.heatalways.chucknorrisfunfacts.di.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.heatalways.chucknorrisfunfacts.data.database.AppDatabase
import ru.heatalways.chucknorrisfunfacts.domain.repositories.chuck_norris_jokes.ChuckNorrisJokesRepository
import ru.heatalways.chucknorrisfunfacts.domain.repositories.chuck_norris_jokes.ChuckNorrisJokesRepositoryImpl
import ru.heatalways.chucknorrisfunfacts.data.network.chuck_norris_jokes.ChuckNorrisJokesApi
import javax.inject.Singleton

@Module(includes = [ApiModule::class])
@InstallIn(SingletonComponent::class)
object ChuckNorrisJokesModule {
    @Provides
    @Singleton
    fun provideChuckNorrisJokesManager(
        api: ChuckNorrisJokesApi,
        database: AppDatabase
    ): ChuckNorrisJokesRepository {
        return ChuckNorrisJokesRepositoryImpl(api, database)
    }
}