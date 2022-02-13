package ru.heatalways.chucknorrisfunfacts.core.di.modules

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [GsonModule::class, HttpClientModule::class])
internal object RetrofitModule {
    @Provides
    @Singleton
    fun provideRetrofitBuilder(gson: Gson, httpClient: OkHttpClient): Retrofit.Builder =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(httpClient)
}