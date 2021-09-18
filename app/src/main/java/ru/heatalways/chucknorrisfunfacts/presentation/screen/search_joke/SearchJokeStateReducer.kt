package ru.heatalways.chucknorrisfunfacts.presentation.screen.search_joke

import ru.heatalways.chucknorrisfunfacts.presentation.base.MviReducer

object SearchJokeStateReducer: MviReducer<
        SearchJokeViewState,
        SearchJokePartialState
> {
    override fun reduce(
        state: SearchJokeViewState,
        partialState: SearchJokePartialState
    ) = when (partialState) {
        is SearchJokePartialState.ScrollUp -> state.copy(
            isScrollingUp = partialState.isScrolling
        )


        is SearchJokePartialState.JokesLoaded -> state.copy(
            isJokesLoading = false,
            jokesMessage = null,
            jokes = partialState.jokes
        )

        is SearchJokePartialState.JokesLoading -> state.copy(
            isJokesLoading = true,
            jokesMessage = null
        )

        is SearchJokePartialState.JokesMessage -> state.copy(
            isJokesLoading = false,
            jokesMessage = partialState.message,
            jokes = emptyList()
        )
    }
}
