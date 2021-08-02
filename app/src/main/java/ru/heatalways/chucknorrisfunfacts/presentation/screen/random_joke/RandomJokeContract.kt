package ru.heatalways.chucknorrisfunfacts.presentation.screen.random_joke

import ru.heatalways.chucknorrisfunfacts.data.entities.Category
import ru.heatalways.chucknorrisfunfacts.data.entities.ChuckJoke
import ru.heatalways.chucknorrisfunfacts.presentation.base.MviEffect
import ru.heatalways.chucknorrisfunfacts.presentation.base.MviAction
import ru.heatalways.chucknorrisfunfacts.presentation.base.MviState

object RandomJokeContract {
    sealed class Action: MviAction {
        class OnRandomJokeRequest(val category: Category): Action()
        object OnCategorySelectionButtonClick: Action()
    }

    sealed class State: MviState {
        object Loading: State()
        object JokeLoading: State()
        object Empty: State()
        class Loaded(val jokes: List<ChuckJoke>): State()
    }

    sealed class Effect: MviEffect {
        object NavigateToCategorySelectionScreen: Effect()
        class Error(val message: String?): Effect()
    }
}