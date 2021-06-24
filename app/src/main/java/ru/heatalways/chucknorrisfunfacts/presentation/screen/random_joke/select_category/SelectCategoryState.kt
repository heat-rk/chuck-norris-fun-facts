package ru.heatalways.chucknorrisfunfacts.presentation.screen.random_joke.select_category

import ru.heatalways.chucknorrisfunfacts.data.entities.Category

sealed class SelectCategoryState {
    object Loading: SelectCategoryState()
    object Empty: SelectCategoryState()
    class Loaded(val categories: List<Category>): SelectCategoryState()
    class Error(val message: String?): SelectCategoryState()
}