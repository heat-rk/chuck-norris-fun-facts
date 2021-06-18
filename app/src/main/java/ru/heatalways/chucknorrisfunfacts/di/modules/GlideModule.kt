package ru.heatalways.chucknorrisfunfacts.di.modules

import dagger.Module
import dagger.Provides
import ru.heatalways.chucknorrisfunfacts.domain.managers.glide.ImageLoaderManager
import ru.heatalways.chucknorrisfunfacts.domain.managers.glide.GlideImageLoaderManagerImpl
import javax.inject.Singleton

@Module
class GlideModule {
    @Provides
    @Singleton
    fun provideGlideService(): ImageLoaderManager = GlideImageLoaderManagerImpl()
}