package ru.heatalways.chucknorrisfunfacts.common.presentation.factories

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

interface ViewModelFactory<T : ViewModel> {
    fun create(handle: SavedStateHandle): T
}