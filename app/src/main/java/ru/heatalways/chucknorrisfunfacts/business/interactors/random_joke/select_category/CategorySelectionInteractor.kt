package ru.heatalways.chucknorrisfunfacts.business.interactors.random_joke.select_category

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import ru.heatalways.chucknorrisfunfacts.business.datasource.network.chuck_norris_jokes.Category

interface CategorySelectionInteractor {
    val selectedCategory: StateFlow<Category>

    fun fetchCategories(): Flow<CategorySelectionPartialState>

    fun searchCategories(query: String): Flow<CategorySelectionPartialState>

    fun selectCategory(category: Category)
}