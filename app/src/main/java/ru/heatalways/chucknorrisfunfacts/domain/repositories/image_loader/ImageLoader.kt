package ru.heatalways.chucknorrisfunfacts.domain.repositories.image_loader

import android.widget.ImageView

interface ImageLoader {
    fun loadImage(config: LoadPhotoConfig, imageView: ImageView)
}