package ru.heatalways.chucknorrisfunfacts.di.modules

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.heatalways.chucknorrisfunfacts.domain.repositories.image_loader.GlideImageLoaderImpl
import ru.heatalways.chucknorrisfunfacts.domain.repositories.image_loader.ImageLoader
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ImageLoaderModule {
    @Binds
    @Singleton
    abstract fun bindImageLoader(
        imageLoaderImpl: GlideImageLoaderImpl
    ): ImageLoader
}