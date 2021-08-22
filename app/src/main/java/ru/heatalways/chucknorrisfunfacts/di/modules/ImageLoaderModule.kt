package ru.heatalways.chucknorrisfunfacts.di.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.heatalways.chucknorrisfunfacts.business.domain.repositories.image_loader.ImageLoader
import ru.heatalways.chucknorrisfunfacts.business.domain.repositories.image_loader.GlideImageLoaderImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ImageLoaderModule {
    @Provides
    @Singleton
    fun provideImageLoaderManager(): ImageLoader = GlideImageLoaderImpl()
}