package ru.heatalways.chucknorrisfunfacts.presentation.screen.random_joke.select_category

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.heatalways.chucknorrisfunfacts.domain.interactors.chuck_norris_jokes.ChuckNorrisJokesInteractor
import ru.heatalways.chucknorrisfunfacts.domain.models.Category
import ru.heatalways.chucknorrisfunfacts.domain.utils.InteractorEvent
import ru.heatalways.chucknorrisfunfacts.presentation.base.MviViewModel
import ru.heatalways.chucknorrisfunfacts.presentation.util.ScrollState
import javax.inject.Inject

@HiltViewModel
class CategorySelectionViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val interactor: ChuckNorrisJokesInteractor
): MviViewModel<
        CategorySelectionAction,
        CategorySelectionViewState,
        CategorySelectionPartialState
>(CategorySelectionStateReducer) {

    override val initialState get() = CategorySelectionViewState(
        isCategoriesLoading = true
    )

    init {
        handleSavedState()
        fetchCategories()
    }

    private fun handleSavedState() {
        savedStateHandle.get<String?>(SAVED_SEARCH_QUERY)?.let { query ->
            search(query)
        }
    }

    fun fetchCategories() {
        if (savedStateHandle.contains(SAVED_SEARCH_QUERY).not())
            interactor.fetchCategories()
                .onEach {
                    when(it) {
                        is InteractorEvent.Error ->
                            reduceState(CategorySelectionPartialState.CategoriesMessage(
                                it.message
                            ))

                        is InteractorEvent.Loading ->
                            reduceState(CategorySelectionPartialState.CategoriesLoading)

                        is InteractorEvent.Success ->
                            reduceState(CategorySelectionPartialState.CategoriesLoaded(
                                it.body
                            ))
                    }
                }
                .launchIn(viewModelScope)
    }

    override fun handleAction(action: CategorySelectionAction) {
        when (action) {
            is CategorySelectionAction.OnCategorySelect ->
                selectCategory(action.category)

            is CategorySelectionAction.OnSearchExecute ->
                search(action.query)
                    .also { savedStateHandle.set(SAVED_SEARCH_QUERY, action.query) }
        }
    }

    private fun selectCategory(category: Category) {
        navigator {
            previousBackStackEntry
                ?.savedStateHandle?.set("category", category)

            navigateUp()
        }
    }

    private fun search(query: String) {
        interactor.searchCategories(query)
            .onEach {
                when(it) {
                    is InteractorEvent.Error ->
                        reduceState(CategorySelectionPartialState.CategoriesMessage(
                            it.message
                        ))

                    is InteractorEvent.Loading ->
                        reduceState(CategorySelectionPartialState.CategoriesLoading)

                    is InteractorEvent.Success -> {
                        reduceState(CategorySelectionPartialState.CategoriesLoaded(
                            it.body
                        ))
                        scrollUp()
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun scrollUp() {
        reduceState(CategorySelectionPartialState.Scroll(ScrollState.ScrollingUp))
        reduceState(CategorySelectionPartialState.Scroll(ScrollState.Stopped))
    }

    companion object {
        private const val SAVED_SEARCH_QUERY =
            "screen.random_joke.select_category.search_query"
    }
}