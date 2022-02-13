package ru.heatalways.chucknorrisfunfacts.common.presentation.utils

import androidx.appcompat.app.AppCompatDelegate

object AppUtils {
    fun setDefaultNightMode(isNightMode: Boolean) {
        AppCompatDelegate.setDefaultNightMode(
            if (isNightMode)
                AppCompatDelegate.MODE_NIGHT_YES
            else
                AppCompatDelegate.MODE_NIGHT_NO
        )
    }
}