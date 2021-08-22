package ru.heatalways.chucknorrisfunfacts.domain.interactors.search_joke

import ru.heatalways.chucknorrisfunfacts.presentation.base.MviReducer

object SearchJokeStateReducer: MviReducer<SearchJokeViewState, SearchJokePartialState>({ partialState ->
    when (partialState) {
        is SearchJokePartialState.Jokes -> {
            copy(
                isLoading = false,
                message = null,
                jokes = partialState.jokes
            )
        }
        is SearchJokePartialState.Loading -> {
            copy(
                isLoading = true,
                message = null
            )
        }
        is SearchJokePartialState.Message -> {
            copy(
                isLoading = false,
                message = partialState.message,
            )
        }
    }
})