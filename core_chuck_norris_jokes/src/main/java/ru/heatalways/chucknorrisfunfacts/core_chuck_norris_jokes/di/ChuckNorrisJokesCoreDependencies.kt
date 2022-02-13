package ru.heatalways.chucknorrisfunfacts.core_chuck_norris_jokes.di

import kotlinx.coroutines.CoroutineDispatcher
import retrofit2.Retrofit
import ru.heatalways.chucknorrisfunfacts.core.data.database.AppDatabase
import ru.heatalways.chucknorrisfunfacts.common.di.IoDispatcher
import ru.heatalways.chucknorrisfunfacts.module_injector.di.BaseModuleDependencies

interface ChuckNorrisJokesCoreDependencies: BaseModuleDependencies {
    fun getAppDatabase(): AppDatabase

    fun getRetrofitBuilder(): Retrofit.Builder

    @IoDispatcher
    fun getIoDispatcher(): CoroutineDispatcher
}