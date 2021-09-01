package ru.heatalways.chucknorrisfunfacts.domain.interactors.random_joke

import ru.heatalways.chucknorrisfunfacts.extensions.editLast
import ru.heatalways.chucknorrisfunfacts.presentation.base.MviReducer

object RandomJokeStateReducer: MviReducer<
        RandomJokeViewState,
        RandomJokePartialState
> {
    override fun reduce(
        state: RandomJokeViewState,
        partialState: RandomJokePartialState
    ) = when (partialState) {
        is RandomJokePartialState.JokeLoaded -> {
            state.copy(
                isJokeLoading = false,
                message = null,
                jokes = listOf(partialState.joke) + state.jokes
            )
        }
        is RandomJokePartialState.JokeLoading -> {
            state.copy(
                isJokeLoading = true
            )
        }
        is RandomJokePartialState.JokeLoadingError -> {
            state.copy(
                isJokeLoading = false
            )
        }


        is RandomJokePartialState.Loading -> {
            state.copy(
                isLoading = true,
                message = null
            )
        }
        is RandomJokePartialState.JokesLoaded -> {
            state.copy(
                isLoading = false,
                message = null,
                jokes = partialState.jokes
            )
        }
        is RandomJokePartialState.JokesUpdating -> {
            state.copy(
                jokes = state.jokes.editLast { copy(isUpdating = true) }
            )
        }
        is RandomJokePartialState.JokesUpdated -> {
            state.copy(
                jokes = state.jokes.editLast { copy(isUpdating = false) } +
                        partialState.jokes
            )
        }


        is RandomJokePartialState.Message -> {
            state.copy(
                isLoading = false,
                message = partialState.message
            )
        }


        is RandomJokePartialState.CategorySelected -> {
            state.copy(
                category = partialState.category
            )
        }
    }
}