package ru.heatalways.chucknorrisfunfacts.di.modules

import dagger.Module
import dagger.Provides
import ru.heatalways.chucknorrisfunfacts.domain.services.glide.GlideService
import ru.heatalways.chucknorrisfunfacts.domain.services.glide.GlideServiceImpl
import javax.inject.Singleton

@Module
class GlideModule {
    @Provides
    @Singleton
    fun provideGlideService(): GlideService = GlideServiceImpl()
}