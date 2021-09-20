package ru.heatalways.chucknorrisfunfacts.domain.utils

import androidx.annotation.DrawableRes

data class LoadPhotoConfig(
    val url: String?,
    @DrawableRes val placeholder: Int? = null
)