package ru.heatalways.chucknorrisfunfacts.presentation.screen.splash

import ru.heatalways.chucknorrisfunfacts.core.base.MviReducer

object SplashReducer: MviReducer<SplashViewState, SplashPartialState> {
    override fun reduce(
        state: SplashViewState,
        partialState: SplashPartialState
    ) = state
}