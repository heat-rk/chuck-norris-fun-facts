package ru.heatalways.chucknorrisfunfacts.domain.interactors.random_joke.select_category

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import ru.heatalways.chucknorrisfunfacts.data.entities.Category

interface CategorySelectionInteractor {
    val selectedCategory: StateFlow<Category>

    fun fetchCategories(): Flow<CategorySelectionPartialState>

    fun searchCategories(query: String): Flow<CategorySelectionPartialState>

    fun selectCategory(category: Category)
}