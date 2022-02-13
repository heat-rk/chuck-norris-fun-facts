package ru.heatalways.chucknorrisfunfacts.random_joke.presentation.screen.random_joke.select_category

import ru.heatalways.chucknorrisfunfacts.core.domain.models.Category
import ru.heatalways.chucknorrisfunfacts.core.presentation.base.MviAction

internal sealed class CategorySelectionAction: MviAction {
    class OnCategorySelect(val category: Category): CategorySelectionAction()
    class OnSearchExecute(val query: String): CategorySelectionAction()
}