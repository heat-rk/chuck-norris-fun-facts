package ru.heatalways.chucknorrisfunfacts.di.modules

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.heatalways.chucknorrisfunfacts.data.network.util.adapter.ApiResponseAdapter
import javax.inject.Singleton

@Module(includes = [GsonModule::class, HttpClientModule::class])
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    @Provides
    @Singleton
    fun provideRetrofitBuilder(gson: Gson, httpClient: OkHttpClient): Retrofit.Builder {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(ApiResponseAdapter.Factory())
            .client(httpClient)
    }
}