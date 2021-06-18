package ru.heatalways.chucknorrisfunfacts.domain.managers.glide

import android.widget.ImageView

interface ImageLoaderManager {
    fun loadImage(config: LoadPhotoConfig, imageView: ImageView)
}