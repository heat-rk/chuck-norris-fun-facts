package ru.heatalways.chucknorrisfunfacts.domain.repositories.image_loader

import androidx.annotation.DrawableRes

data class LoadPhotoConfig(
    val url: String?,
    @DrawableRes val placeholder: Int? = null
)