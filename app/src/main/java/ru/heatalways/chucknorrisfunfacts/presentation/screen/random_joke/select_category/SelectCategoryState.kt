package ru.heatalways.chucknorrisfunfacts.presentation.screen.random_joke.select_category

sealed class SelectCategoryState {
    object CategoriesLoading: SelectCategoryState()
    class CategoriesLoaded(val categories: List<String>): SelectCategoryState()
    class CategoriesLoadError(val message: String?): SelectCategoryState()
}