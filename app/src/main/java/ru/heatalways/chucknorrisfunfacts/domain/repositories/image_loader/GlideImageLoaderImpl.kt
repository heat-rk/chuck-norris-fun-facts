package ru.heatalways.chucknorrisfunfacts.domain.repositories.image_loader

import android.widget.ImageView
import com.bumptech.glide.Glide

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