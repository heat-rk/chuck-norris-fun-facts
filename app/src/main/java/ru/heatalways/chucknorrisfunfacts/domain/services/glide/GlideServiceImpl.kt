package ru.heatalways.chucknorrisfunfacts.domain.services.glide

import android.widget.ImageView
import com.bumptech.glide.Glide

class GlideServiceImpl: GlideService {
    override fun loadImage(config: LoadPhotoConfig, imageView: ImageView) {
        var builder = Glide
            .with(imageView.context)
            .load(config.url)
            .placeholder(config.placeholder ?: -1)
            .error(config.placeholder ?: -1)
            .into(imageView)
    }
}