package ru.heatalways.chucknorrisfunfacts.common.presentation.utils

import androidx.annotation.DrawableRes

data class LoadPhotoConfig(
    val url: String?,
    @DrawableRes val placeholder: Int? = null
)