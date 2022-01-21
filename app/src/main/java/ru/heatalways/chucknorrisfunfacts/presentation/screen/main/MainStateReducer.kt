package ru.heatalways.chucknorrisfunfacts.presentation.screen.main

import ru.heatalways.chucknorrisfunfacts.core.base.MviReducer

object MainStateReducer: MviReducer<MainViewState, MainPartialState> {
    override fun reduce(
        state: MainViewState,
        partialState: MainPartialState
    ): MainViewState = state
}