package ru.heatalways.chucknorrisfunfacts.presentation.screen.random_joke.select_category

import ru.heatalways.chucknorrisfunfacts.domain.models.Category
import ru.heatalways.chucknorrisfunfacts.core.models.StringResource
import ru.heatalways.chucknorrisfunfacts.core.base.MviState
import ru.heatalways.chucknorrisfunfacts.presentation.util.ScrollState

data class CategorySelectionViewState(
    val isCategoriesLoading: Boolean = false,
    val categories: List<Category> = emptyList(),
    val categoriesMessage: StringResource? = null,
    val scrollState: ScrollState = ScrollState.Stopped
): MviState