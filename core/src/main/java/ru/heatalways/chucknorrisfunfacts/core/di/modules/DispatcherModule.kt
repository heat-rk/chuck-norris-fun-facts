package ru.heatalways.chucknorrisfunfacts.core.di.modules

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import ru.heatalways.chucknorrisfunfacts.common.di.DefaultDispatcher
import ru.heatalways.chucknorrisfunfacts.common.di.IoDispatcher
import ru.heatalways.chucknorrisfunfacts.common.di.MainDispatcher

@Module
internal object DispatcherModule {
    @DefaultDispatcher
    @Provides
    fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @IoDispatcher
    @Provides
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @MainDispatcher
    @Provides
    fun providesMainDispatcher(): CoroutineDispatcher = Dispatchers.Main
}