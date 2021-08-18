package ru.heatalways.chucknorrisfunfacts.business.interactors.random_joke.select_category

import ru.heatalways.chucknorrisfunfacts.presentation.base.MviReducer

object CategorySelectionStateReducer: MviReducer<
        CategorySelectionState,
        CategorySelectionPartialState
>({ partialState ->

    when (partialState) {
        is CategorySelectionPartialState.Categories -> {
            copy(
                isLoading = false,
                message = null,
                categories = partialState.categories
            )
        }
        is CategorySelectionPartialState.Loading -> {
            copy(
                isLoading = true,
                message = null
            )
        }
        is CategorySelectionPartialState.Message -> {
            copy(
                isLoading = false,
                message = partialState.message
            )
        }
    }
})