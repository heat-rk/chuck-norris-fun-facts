package ru.heatalways.chucknorrisfunfacts.presentation.screen.random_joke.select_category

import ru.heatalways.chucknorrisfunfacts.domain.repositories.chuck_norris_jokes.Category
import ru.heatalways.chucknorrisfunfacts.domain.utils.StringResource
import ru.heatalways.chucknorrisfunfacts.presentation.util.ScrollState

sealed class CategorySelectionPartialState {
    data class Scroll(val scrollState: ScrollState): CategorySelectionPartialState()

    object CategoriesLoading: CategorySelectionPartialState()

    data class CategoriesLoaded(
        val categories: List<Category>
    ): CategorySelectionPartialState()

    data class CategoriesMessage(
        val message: StringResource?
    ): CategorySelectionPartialState()
}