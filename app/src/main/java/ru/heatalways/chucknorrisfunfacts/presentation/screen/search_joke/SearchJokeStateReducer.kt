package ru.heatalways.chucknorrisfunfacts.presentation.screen.search_joke

import ru.heatalways.chucknorrisfunfacts.core.base.MviReducer

object SearchJokeStateReducer: MviReducer<
        SearchJokeViewState,
        SearchJokePartialState
> {
    override fun reduce(
        state: SearchJokeViewState,
        partialState: SearchJokePartialState
    ) = when (partialState) {
        is SearchJokePartialState.Scroll -> state.copy(
            scrollState = partialState.scrollState
        )


        is SearchJokePartialState.JokesLoaded -> state.copy(
            isJokesLoading = false,
            jokesMessage = null,
            jokes = partialState.jokes
        )

        is SearchJokePartialState.JokesLoading -> state.copy(
            isJokesLoading = true,
            jokesMessage = null,
            jokes = emptyList()
        )

        is SearchJokePartialState.JokesMessage -> state.copy(
            isJokesLoading = false,
            jokesMessage = partialState.message,
            jokes = emptyList()
        )
    }
}
