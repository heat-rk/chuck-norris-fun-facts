package ru.heatalways.chucknorrisfunfacts.domain.interactors.random_joke.select_category

import ru.heatalways.chucknorrisfunfacts.presentation.base.MviReducer

object CategorySelectionStateReducer: MviReducer<
        CategorySelectionState,
        CategorySelectionPartialState
>{
    override fun reduce(
        state: CategorySelectionState,
        partialState: CategorySelectionPartialState
    ) = when (partialState) {
        is CategorySelectionPartialState.Categories -> {
            state.copy(
                isLoading = false,
                message = null,
                categories = partialState.categories
            )
        }
        is CategorySelectionPartialState.Loading -> {
            state.copy(
                isLoading = true,
                message = null
            )
        }
        is CategorySelectionPartialState.Message -> {
            state.copy(
                isLoading = false,
                message = partialState.message
            )
        }
    }
}