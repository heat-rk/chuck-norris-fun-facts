package ru.heatalways.chucknorrisfunfacts.presentation.screen.main

import ru.heatalways.chucknorrisfunfacts.presentation.base.MviViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor(): MviViewModel<
        MainAction,
        MainViewState,
        MainPartialState
>(MainStateReducer) {
    override val initialState: MainViewState get() = MainViewState()
    override fun handleAction(action: MainAction) = Unit
}