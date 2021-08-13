package ru.heatalways.chucknorrisfunfacts.presentation.screen.random_joke

import ru.heatalways.chucknorrisfunfacts.data.entities.Category
import ru.heatalways.chucknorrisfunfacts.data.entities.ChuckJoke
import ru.heatalways.chucknorrisfunfacts.data.utils.StringResource
import ru.heatalways.chucknorrisfunfacts.presentation.base.MviAction
import ru.heatalways.chucknorrisfunfacts.presentation.base.MviEffect
import ru.heatalways.chucknorrisfunfacts.presentation.base.MviReducer
import ru.heatalways.chucknorrisfunfacts.presentation.base.MviState

object RandomJokeContract {
    sealed class Action: MviAction {
        class OnRandomJokeRequest(val category: Category): Action()
        object OnCategorySelectionButtonClick: Action()
    }

    data class State(
        val isLoading: Boolean = false,
        val isJokeLoading: Boolean = false,
        val message: StringResource? = null,
        val jokes: List<ChuckJoke> = emptyList()
    ): MviState

    sealed class PartialState {
        object Loading: PartialState()
        object JokeLoading: PartialState()
        class Message(val message: StringResource): PartialState()
        class JokeLoaded(val joke: ChuckJoke): PartialState()
        class JokesLoaded(val jokes: List<ChuckJoke>): PartialState()
    }

    object Reducer: MviReducer<State, PartialState>({ partialState ->
        when (partialState) {
            is PartialState.JokeLoaded -> {
                copy(
                    isJokeLoading = false,
                    message = null,
                    jokes = listOf(partialState.joke) + jokes
                )
            }
            is PartialState.JokeLoading -> {
                copy(
                    isJokeLoading = true
                )
            }
            is PartialState.JokesLoaded -> {
                copy(
                    isLoading = false,
                    message = null,
                    jokes = partialState.jokes
                )
            }
            is PartialState.Loading -> {
                copy(
                    isLoading = true,
                    message = null
                )
            }
            is PartialState.Message -> {
                copy(
                    isLoading = false,
                    message = partialState.message
                )
            }
        }
    })

    sealed class Effect: MviEffect {
        object NavigateToCategorySelectionScreen: Effect()
        class Error(val message: String?): Effect()
    }
}