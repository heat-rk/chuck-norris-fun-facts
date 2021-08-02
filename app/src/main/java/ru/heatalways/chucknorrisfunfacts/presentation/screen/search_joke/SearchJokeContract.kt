package ru.heatalways.chucknorrisfunfacts.presentation.screen.search_joke

import ru.heatalways.chucknorrisfunfacts.data.entities.ChuckJoke
import ru.heatalways.chucknorrisfunfacts.presentation.base.MviEffect
import ru.heatalways.chucknorrisfunfacts.presentation.base.MviAction
import ru.heatalways.chucknorrisfunfacts.presentation.base.MviState

object SearchJokeContract {
    sealed class Action: MviAction {
        class OnSearchExecute(val query: String): Action()
    }

    sealed class State: MviState {
        object Default: State()
        object Loading: State()
        object Empty: State()
        class Loaded(val jokes: List<ChuckJoke>): State()
        class Error(val message: String?): State()
    }

    sealed class Effect: MviEffect
}