package ru.heatalways.chucknorrisfunfacts.random_joke.presentation.screen.random_joke.select_category

import ru.heatalways.chucknorrisfunfacts.core.domain.models.Category
import ru.heatalways.chucknorrisfunfacts.core.presentation.base.MviState
import ru.heatalways.chucknorrisfunfacts.common.general.utils.StringResource
import ru.heatalways.chucknorrisfunfacts.common.presentation.utils.ScrollState

internal data class CategorySelectionViewState(
    val isCategoriesLoading: Boolean = false,
    val categories: List<Category> = emptyList(),
    val categoriesMessage: StringResource? = null,
    val scrollState: ScrollState = ScrollState.Stopped
): MviState