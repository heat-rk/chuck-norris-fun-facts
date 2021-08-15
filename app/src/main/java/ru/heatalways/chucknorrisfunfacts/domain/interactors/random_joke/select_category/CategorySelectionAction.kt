package ru.heatalways.chucknorrisfunfacts.domain.interactors.random_joke.select_category

import ru.heatalways.chucknorrisfunfacts.data.entities.Category
import ru.heatalways.chucknorrisfunfacts.presentation.base.MviAction

sealed class CategorySelectionAction: MviAction {
    class OnCategorySelect(val category: Category): CategorySelectionAction()
    class OnSearchExecute(val query: String): CategorySelectionAction()
}