package ru.heatalways.chucknorrisfunfacts.presentation.screen.random_joke.select_category

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.heatalways.chucknorrisfunfacts.R
import ru.heatalways.chucknorrisfunfacts.data.entities.Category
import ru.heatalways.chucknorrisfunfacts.data.utils.StringResource
import ru.heatalways.chucknorrisfunfacts.domain.managers.chuck_norris_jokes.ChuckNorrisJokesManager
import ru.heatalways.chucknorrisfunfacts.presentation.base.BaseMviViewModel
import javax.inject.Inject

@HiltViewModel
class CategorySelectionViewModel @Inject constructor(
    private val jokesManager: ChuckNorrisJokesManager
): BaseMviViewModel<
        CategorySelectionContract.Action,
        CategorySelectionContract.State,
        CategorySelectionContract.Effect,
        CategorySelectionContract.PartialState
>(CategorySelectionContract.Reducer) {
    override val initialState get() = CategorySelectionContract.State(
        isLoading = true
    )

    override fun handleAction(action: CategorySelectionContract.Action) {
        when (action) {
            is CategorySelectionContract.Action.OnCategoryClick -> {
                setEffect(CategorySelectionContract.Effect.GoBack)
            }
            is CategorySelectionContract.Action.OnSearchExecute -> {
                searchCategories(action.query)
            }
        }
    }

    init {
        fetchCategories()
    }

    private fun fetchCategories() {
        viewModelScope.launch {
            val response = jokesManager.categories()
            if (response.isOk && response.value != null) {
                val categories = listOf(Category.Any).plus(response.value.map {
                    Category.Specific(it)
                })

                reduceState(partialCategories(categories))
            } else {
                reduceState(partialMessage(strRes(response.error?.message)))
            }
        }
    }

    private fun searchCategories(query: String) {
        viewModelScope.launch {
            reduceState(partialLoading())

            val results = if (query.isEmpty())
                currentState.categories
            else
                currentState.categories.filter { it is Category.Specific && it.name.contains(query) }

            if (results.isNotEmpty())
                reduceState(partialCategories(results))
            else
                reduceState(partialMessage(strRes(R.string.error_not_found)))
        }
    }

    private fun partialCategories(categories: List<Category>) =
        CategorySelectionContract.PartialState.Categories(categories)

    private fun partialMessage(message: StringResource) =
        CategorySelectionContract.PartialState.Message(message)

    private fun partialLoading() =
        CategorySelectionContract.PartialState.Loading
}