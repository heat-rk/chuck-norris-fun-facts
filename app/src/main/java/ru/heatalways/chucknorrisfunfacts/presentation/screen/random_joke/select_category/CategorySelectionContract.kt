package ru.heatalways.chucknorrisfunfacts.presentation.screen.random_joke.select_category

import ru.heatalways.chucknorrisfunfacts.data.entities.Category
import ru.heatalways.chucknorrisfunfacts.data.utils.StringResource
import ru.heatalways.chucknorrisfunfacts.presentation.base.MviAction
import ru.heatalways.chucknorrisfunfacts.presentation.base.MviEffect
import ru.heatalways.chucknorrisfunfacts.presentation.base.MviReducer
import ru.heatalways.chucknorrisfunfacts.presentation.base.MviState

object CategorySelectionContract {
    sealed class Action: MviAction {
        object OnCategoryClick: Action()
        class OnSearchExecute(val query: String): Action()
    }

    data class State(
        val isLoading: Boolean = false,
        val categories: List<Category> = emptyList(),
        val message: StringResource? = null
    ): MviState

    sealed class PartialState {
        object Loading: PartialState()
        class Categories(val categories: List<Category>): PartialState()
        class Message(val message: StringResource?): PartialState()
    }

    object Reducer: MviReducer<State, PartialState>({ partialState ->
        when (partialState) {
            is PartialState.Categories -> {
                copy(
                    isLoading = false,
                    message = null,
                    categories = partialState.categories
                )
            }
            is PartialState.Loading -> {
                copy(
                    isLoading = true,
                    message = null
                )
            }
            is PartialState.Message -> {
                copy(
                    isLoading = false,
                    message = partialState.message
                )
            }
        }
    })

    sealed class Effect: MviEffect {
        object GoBack: Effect()
    }
}