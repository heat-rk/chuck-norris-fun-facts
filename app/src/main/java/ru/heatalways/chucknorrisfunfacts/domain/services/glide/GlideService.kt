package ru.heatalways.chucknorrisfunfacts.domain.services.glide

import android.widget.ImageView

interface GlideService {
    fun loadImage(config: LoadPhotoConfig, imageView: ImageView)
}