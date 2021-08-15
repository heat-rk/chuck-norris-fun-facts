package ru.heatalways.chucknorrisfunfacts.di.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.heatalways.chucknorrisfunfacts.domain.managers.glide.ImageLoaderManager
import ru.heatalways.chucknorrisfunfacts.domain.managers.glide.GlideImageLoaderManagerImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ImageLoaderModule {
    @Provides
    @Singleton
    fun provideImageLoaderManager(): ImageLoaderManager = GlideImageLoaderManagerImpl()
}