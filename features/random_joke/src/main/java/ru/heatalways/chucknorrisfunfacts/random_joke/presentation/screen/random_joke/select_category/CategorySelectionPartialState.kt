package ru.heatalways.chucknorrisfunfacts.random_joke.presentation.screen.random_joke.select_category

import ru.heatalways.chucknorrisfunfacts.core.domain.models.Category
import ru.heatalways.chucknorrisfunfacts.common.general.utils.StringResource
import ru.heatalways.chucknorrisfunfacts.common.presentation.utils.ScrollState

internal sealed class CategorySelectionPartialState {
    data class Scroll(val scrollState: ScrollState): CategorySelectionPartialState()

    object CategoriesLoading: CategorySelectionPartialState()

    data class CategoriesLoaded(
        val categories: List<Category>
    ): CategorySelectionPartialState()

    data class CategoriesMessage(
        val message: StringResource?
    ): CategorySelectionPartialState()
}