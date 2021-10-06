package ru.heatalways.chucknorrisfunfacts.presentation.screen.main

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.heatalways.chucknorrisfunfacts.presentation.base.MviViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(): MviViewModel<
        MainAction,
        MainViewState,
        MainPartialState
>(MainStateReducer) {
    override val initialState: MainViewState get() = MainViewState()
    override fun handleAction(action: MainAction) = Unit
}