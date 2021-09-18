package ru.heatalways.chucknorrisfunfacts.presentation.screen.random_joke.select_category

import ru.heatalways.chucknorrisfunfacts.presentation.base.MviReducer

object CategorySelectionStateReducer: MviReducer<
        CategorySelectionState,
        CategorySelectionPartialState
>{
    override fun reduce(
        state: CategorySelectionState,
        partialState: CategorySelectionPartialState
    ) = when (partialState) {
        is CategorySelectionPartialState.ScrollUp -> state.copy(
            isScrollingUp = partialState.isScrolling
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