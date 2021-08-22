package ru.heatalways.chucknorrisfunfacts.business.interactors.random_joke.select_category

import ru.heatalways.chucknorrisfunfacts.business.domain.repositories.chuck_norris_jokes.Category
import ru.heatalways.chucknorrisfunfacts.business.domain.utils.StringResource
import ru.heatalways.chucknorrisfunfacts.presentation.base.MviState

data class CategorySelectionState(
    val isLoading: Boolean = false,
    val categories: List<Category> = emptyList(),
    val message: StringResource? = null
): MviState