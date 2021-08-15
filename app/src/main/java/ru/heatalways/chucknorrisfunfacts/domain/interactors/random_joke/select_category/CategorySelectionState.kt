package ru.heatalways.chucknorrisfunfacts.domain.interactors.random_joke.select_category

import ru.heatalways.chucknorrisfunfacts.data.entities.Category
import ru.heatalways.chucknorrisfunfacts.data.utils.StringResource
import ru.heatalways.chucknorrisfunfacts.presentation.base.MviState

data class CategorySelectionState(
    val isLoading: Boolean = false,
    val categories: List<Category> = emptyList(),
    val message: StringResource? = null
): MviState