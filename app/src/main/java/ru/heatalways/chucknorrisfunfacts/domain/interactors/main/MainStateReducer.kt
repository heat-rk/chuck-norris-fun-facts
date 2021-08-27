package ru.heatalways.chucknorrisfunfacts.domain.interactors.main

import ru.heatalways.chucknorrisfunfacts.presentation.base.MviReducer

object MainStateReducer: MviReducer<MainViewState, MainPartialState> {
    override fun reduce(
        state: MainViewState,
        partialState: MainPartialState
    ): MainViewState {
        return MainViewState()
    }
}