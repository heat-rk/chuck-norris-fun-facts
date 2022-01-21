package ru.heatalways.chucknorrisfunfacts.presentation.screen.random_joke

import ru.heatalways.chucknorrisfunfacts.extensions.editLast
import ru.heatalways.chucknorrisfunfacts.extensions.singleList
import ru.heatalways.chucknorrisfunfacts.core.base.MviReducer

object RandomJokeStateReducer: MviReducer<
        RandomJokeViewState,
        RandomJokePartialState
> {
    override fun reduce(
        state: RandomJokeViewState,
        partialState: RandomJokePartialState
    ) = when (partialState) {
        is RandomJokePartialState.JokeLoaded -> state.copy(
            isJokeLoading = false,
            message = null,
            jokes = partialState.joke.singleList() + state.jokes
        )

        is RandomJokePartialState.JokeLoading -> state.copy(
            isJokeLoading = true
        )



        is RandomJokePartialState.JokesLoading -> state.copy(
            isLoading = true,
            message = null
        )

        is RandomJokePartialState.JokesLoaded -> state.copy(
            isLoading = false,
            message = null,
            jokes = partialState.jokes
        )

        is RandomJokePartialState.JokesUpdating -> state.copy(
            jokes = state.jokes.toMutableList().editLast { copy(isUpdating = true) }
        )

        is RandomJokePartialState.JokesUpdated -> state.copy(
            jokes = state.jokes.toMutableList().editLast { copy(isUpdating = false) } +
                    partialState.jokes
        )
        is RandomJokePartialState.JokesMessage -> state.copy(
            isLoading = false,
            message = partialState.message,
            jokes = emptyList()
        )


        is RandomJokePartialState.Toast -> state.copy(
            toastState = partialState.toastState
        )
        is RandomJokePartialState.Snackbar -> state.copy(
            snackbarState = partialState.snackbarState
        )
        is RandomJokePartialState.Scroll -> state.copy(
            scrollState = partialState.scrollState
        )


        is RandomJokePartialState.CategorySelected -> state.copy(
            category = partialState.category
        )
    }
}