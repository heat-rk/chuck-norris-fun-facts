package ru.heatalways.chucknorrisfunfacts.business.interactors.random_joke.select_category

import ru.heatalways.chucknorrisfunfacts.business.domain.repositories.chuck_norris_jokes.Category
import ru.heatalways.chucknorrisfunfacts.presentation.base.MviAction

sealed class CategorySelectionAction: MviAction {
    class OnCategorySelect(val category: Category): CategorySelectionAction()
    class OnSearchExecute(val query: String): CategorySelectionAction()
}