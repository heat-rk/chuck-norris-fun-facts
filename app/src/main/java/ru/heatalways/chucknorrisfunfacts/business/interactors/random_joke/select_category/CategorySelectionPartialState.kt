package ru.heatalways.chucknorrisfunfacts.business.interactors.random_joke.select_category

import ru.heatalways.chucknorrisfunfacts.business.domain.repositories.chuck_norris_jokes.Category
import ru.heatalways.chucknorrisfunfacts.business.domain.utils.StringResource

sealed class CategorySelectionPartialState {
    object Loading: CategorySelectionPartialState()
    class Categories(val categories: List<Category>): CategorySelectionPartialState()
    class Message(val message: StringResource?): CategorySelectionPartialState()
}