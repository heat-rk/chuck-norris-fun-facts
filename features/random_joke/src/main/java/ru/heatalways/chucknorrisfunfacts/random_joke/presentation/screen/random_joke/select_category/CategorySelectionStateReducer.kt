package ru.heatalways.chucknorrisfunfacts.random_joke.presentation.screen.random_joke.select_category

import ru.heatalways.chucknorrisfunfacts.core.presentation.base.MviReducer

internal object CategorySelectionStateReducer: MviReducer<
        CategorySelectionViewState,
        CategorySelectionPartialState
        > {
    override fun reduce(
        state: CategorySelectionViewState,
        partialState: CategorySelectionPartialState
    ) = when (partialState) {
        is CategorySelectionPartialState.Scroll -> state.copy(
            scrollState = partialState.scrollState
        )

        is CategorySelectionPartialState.CategoriesLoaded -> state.copy(
            isCategoriesLoading = false,
            categoriesMessage = null,
            categories = partialState.categories
        )

        is CategorySelectionPartialState.CategoriesLoading -> state.copy(
            isCategoriesLoading = true,
            categoriesMessage = null
        )

        is CategorySelectionPartialState.CategoriesMessage -> state.copy(
            isCategoriesLoading = false,
            categoriesMessage = partialState.message,
            categories = emptyList()
        )

    }
}