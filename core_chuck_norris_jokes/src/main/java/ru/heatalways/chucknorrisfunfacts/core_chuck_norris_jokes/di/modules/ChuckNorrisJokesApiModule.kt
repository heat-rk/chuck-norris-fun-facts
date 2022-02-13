package ru.heatalways.chucknorrisfunfacts.core_chuck_norris_jokes.di.modules

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.heatalways.chucknorrisfunfacts.core_chuck_norris_jokes.data.network.chuck_norris_jokes.ChuckNorrisJokesApi
import javax.inject.Singleton

@Module
internal object ChuckNorrisJokesApiModule {
    @Provides
    @Singleton
    fun provideChuckNorrisJokesApi(builder: Retrofit.Builder): ChuckNorrisJokesApi {
        return buildChuckNorrisJokesRetrofit(builder).create(ChuckNorrisJokesApi::class.java)
    }

    private fun buildChuckNorrisJokesRetrofit(builder: Retrofit.Builder): Retrofit {
        return builder.baseUrl(ChuckNorrisJokesApi.BASE_URL).build()
    }
}