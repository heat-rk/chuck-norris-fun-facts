package ru.heatalways.chucknorrisfunfacts.presentation.util

import androidx.annotation.DrawableRes

data class LoadPhotoConfig(
    val url: String?,
    @DrawableRes val placeholder: Int? = null
)