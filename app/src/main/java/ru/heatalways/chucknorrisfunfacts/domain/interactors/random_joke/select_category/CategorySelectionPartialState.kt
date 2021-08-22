package ru.heatalways.chucknorrisfunfacts.domain.interactors.random_joke.select_category

import ru.heatalways.chucknorrisfunfacts.domain.repositories.chuck_norris_jokes.Category
import ru.heatalways.chucknorrisfunfacts.domain.utils.StringResource

sealed class CategorySelectionPartialState {
    object Loading: CategorySelectionPartialState()
    class Categories(val categories: List<Category>): CategorySelectionPartialState()
    class Message(val message: StringResource?): CategorySelectionPartialState()
}