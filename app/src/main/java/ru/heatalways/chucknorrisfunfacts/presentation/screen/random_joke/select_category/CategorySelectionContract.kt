package ru.heatalways.chucknorrisfunfacts.presentation.screen.random_joke.select_category

import ru.heatalways.chucknorrisfunfacts.data.entities.Category
import ru.heatalways.chucknorrisfunfacts.presentation.base.MviAction
import ru.heatalways.chucknorrisfunfacts.presentation.base.MviEffect
import ru.heatalways.chucknorrisfunfacts.presentation.base.MviState

object CategorySelectionContract {
    sealed class Action: MviAction {
        object OnCategoryClick: Action()
        class OnSearchExecute(val query: String): Action()
    }

    sealed class State: MviState {
        object Loading: State()
        object Empty: State()
        class Loaded(val categories: List<Category>): State()
        class Error(val message: String?): State()
    }

    sealed class Effect: MviEffect {
        object GoBack: Effect()
    }
}