package ru.heatalways.chucknorrisfunfacts.presentation.screen.search_joke

import ru.heatalways.chucknorrisfunfacts.data.entities.ChuckJoke
import ru.heatalways.chucknorrisfunfacts.data.utils.StringResource
import ru.heatalways.chucknorrisfunfacts.presentation.base.MviEffect
import ru.heatalways.chucknorrisfunfacts.presentation.base.MviAction
import ru.heatalways.chucknorrisfunfacts.presentation.base.MviReducer
import ru.heatalways.chucknorrisfunfacts.presentation.base.MviState

object SearchJokeContract {
    sealed class Action: MviAction {
        class OnSearchExecute(val query: String): Action()
    }

    data class State(
        val isLoading: Boolean = false,
        val message: StringResource? = null,
        val jokes: List<ChuckJoke> = emptyList()
    ): MviState

    sealed class PartialState {
        object Loading: PartialState()
        class Message(val message: StringResource): PartialState()
        class Jokes(val jokes: List<ChuckJoke>): PartialState()
    }

    object Reducer: MviReducer<State, PartialState>({ partialState ->
        when (partialState) {
            is PartialState.Jokes -> {
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
                    message = partialState.message,
                )
            }
        }
    })

    sealed class Effect: MviEffect
}