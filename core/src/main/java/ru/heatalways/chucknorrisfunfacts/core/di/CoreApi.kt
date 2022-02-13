package ru.heatalways.chucknorrisfunfacts.core.di

import com.google.gson.Gson
import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import ru.heatalways.chucknorrisfunfacts.common.di.DefaultDispatcher
import ru.heatalways.chucknorrisfunfacts.common.di.IoDispatcher
import ru.heatalways.chucknorrisfunfacts.common.di.MainDispatcher
import ru.heatalways.chucknorrisfunfacts.core.data.database.AppDatabase
import ru.heatalways.chucknorrisfunfacts.module_injector.di.BaseModuleApi

interface CoreApi: BaseModuleApi {
    fun getAppDatabase(): AppDatabase

    fun getGson(): Gson

    fun getHttpClient(): OkHttpClient

    fun getRetrofitBuilder(): Retrofit.Builder

    @DefaultDispatcher
    fun getDefaultDispatcher(): CoroutineDispatcher

    @IoDispatcher
    fun getIoDispatcher(): CoroutineDispatcher

    @MainDispatcher
    fun getMainDispatcher(): CoroutineDispatcher
}