package ru.heatalways.chucknorrisfunfacts.business.datasource.repositories.image_loader

import android.widget.ImageView
import com.bumptech.glide.Glide
import ru.heatalways.chucknorrisfunfacts.business.domain.repositories.image_loader.ImageLoader
import ru.heatalways.chucknorrisfunfacts.business.domain.repositories.image_loader.LoadPhotoConfig

class GlideImageLoaderImpl: ImageLoader {
    override fun loadImage(config: LoadPhotoConfig, imageView: ImageView) {
        var builder = Glide
            .with(imageView.context)
            .load(config.url)
            .placeholder(config.placeholder ?: -1)
            .error(config.placeholder ?: -1)
            .into(imageView)
    }
}