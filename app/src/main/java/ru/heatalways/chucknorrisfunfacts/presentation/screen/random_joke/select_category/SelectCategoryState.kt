package ru.heatalways.chucknorrisfunfacts.presentation.screen.random_joke.select_category

import ru.heatalways.chucknorrisfunfacts.data.entities.Category

sealed class SelectCategoryState {
    object CategoriesLoading: SelectCategoryState()
    class CategoriesLoaded(val categories: List<Category>): SelectCategoryState()
    class CategoriesLoadError(val message: String?): SelectCategoryState()
}