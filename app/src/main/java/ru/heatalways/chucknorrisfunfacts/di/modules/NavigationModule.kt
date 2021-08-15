package ru.heatalways.chucknorrisfunfacts.di.modules

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NavigationModule {
    @Provides
    @Singleton
    fun provideCicerone() = Cicerone.create()

    @Provides
    @Singleton
    fun provideRouter(cicerone: Cicerone<Router>) = cicerone.router
}