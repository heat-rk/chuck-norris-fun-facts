package ru.heatalways.chucknorrisfunfacts.domain.managers.glide

import android.widget.ImageView
import com.bumptech.glide.Glide

class GlideImageLoaderManagerImpl: ImageLoaderManager {
    override fun loadImage(config: LoadPhotoConfig, imageView: ImageView) {
        var builder = Glide
            .with(imageView.context)
            .load(config.url)
            .placeholder(config.placeholder ?: -1)
            .error(config.placeholder ?: -1)
            .into(imageView)
    }
}