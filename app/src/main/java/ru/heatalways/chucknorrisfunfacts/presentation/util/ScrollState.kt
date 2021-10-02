package ru.heatalways.chucknorrisfunfacts.presentation.util

import kotlinx.coroutines.flow.flow

sealed class ScrollState {
    object ScrollingUp: ScrollState()
    object Stopped: ScrollState()

    companion object {
        fun upScrolls() = flow {
            emit(ScrollingUp)
            emit(Stopped)
        }
    }
}
