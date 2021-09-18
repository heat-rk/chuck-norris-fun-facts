package ru.heatalways.chucknorrisfunfacts.presentation.screen.random_joke.select_category

import ru.heatalways.chucknorrisfunfacts.domain.repositories.chuck_norris_jokes.Category
import ru.heatalways.chucknorrisfunfacts.domain.utils.StringResource
import ru.heatalways.chucknorrisfunfacts.presentation.base.MviState

data class CategorySelectionState(
    val isCategoriesLoading: Boolean = false,
    val categories: List<Category> = emptyList(),
    val categoriesMessage: StringResource? = null,
    val isScrollingUp: Boolean = false
): MviState