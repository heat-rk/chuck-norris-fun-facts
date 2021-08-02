package ru.heatalways.chucknorrisfunfacts.presentation.screen.random_joke.select_category

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.heatalways.chucknorrisfunfacts.data.entities.Category
import ru.heatalways.chucknorrisfunfacts.domain.managers.chuck_norris_jokes.ChuckNorrisJokesManager
import ru.heatalways.chucknorrisfunfacts.presentation.base.BaseMviViewModel
import javax.inject.Inject

@HiltViewModel
class SelectCategoryViewModel @Inject constructor(
    private val jokesManager: ChuckNorrisJokesManager
): BaseMviViewModel<
        CategorySelectionContract.Action,
        CategorySelectionContract.State,
        CategorySelectionContract.Effect
>() {
    var categories = emptyList<Category>()

    override val initialState get() = CategorySelectionContract.State.Loading

    override fun handleAction(action: CategorySelectionContract.Action) {
        when (action) {
            is CategorySelectionContract.Action.OnCategoryClick -> {
                setEffect { CategorySelectionContract.Effect.GoBack }
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
                categories = listOf(Category.Any).plus(response.value.map {
                    Category.Specific(it)
                })
                setState { CategorySelectionContract.State.Loaded(categories) }
            } else {
                setState { CategorySelectionContract.State.Error(response.error?.message) }
            }
        }
    }

    private fun searchCategories(query: String) {
        viewModelScope.launch {
            setState { CategorySelectionContract.State.Loading }

            val results = if (query.isEmpty())
                categories
            else
                categories.filter { it is Category.Specific && it.name.contains(query) }

            if (results.isNotEmpty())
                setState { CategorySelectionContract.State.Loaded(results) }
            else
                setState { CategorySelectionContract.State.Empty }
        }
    }
}