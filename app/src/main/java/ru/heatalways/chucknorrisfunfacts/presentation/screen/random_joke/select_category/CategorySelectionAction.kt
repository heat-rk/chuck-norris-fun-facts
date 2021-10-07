package ru.heatalways.chucknorrisfunfacts.presentation.screen.random_joke.select_category

import ru.heatalways.chucknorrisfunfacts.domain.models.Category
import ru.heatalways.chucknorrisfunfacts.presentation.base.MviAction

sealed class CategorySelectionAction: MviAction {
    class OnCategorySelect(val category: Category): CategorySelectionAction()
    class OnSearchExecute(val query: String): CategorySelectionAction()
}