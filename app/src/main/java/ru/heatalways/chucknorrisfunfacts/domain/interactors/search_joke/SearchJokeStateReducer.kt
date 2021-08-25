package ru.heatalways.chucknorrisfunfacts.domain.interactors.search_joke

import ru.heatalways.chucknorrisfunfacts.presentation.base.MviReducer

object SearchJokeStateReducer: MviReducer<
        SearchJokeViewState,
        SearchJokePartialState
> {
    override fun reduce(
        state: SearchJokeViewState,
        partialState: SearchJokePartialState
    ) = when (partialState) {
        is SearchJokePartialState.Jokes -> {
            state.copy(
                isLoading = false,
                message = null,
                jokes = partialState.jokes
            )
        }
        is SearchJokePartialState.Loading -> {
            state.copy(
                isLoading = true,
                message = null
            )
        }
        is SearchJokePartialState.Message -> {
            state.copy(
                isLoading = false,
                message = partialState.message,
                jokes = emptyList()
            )
        }
    }
}
