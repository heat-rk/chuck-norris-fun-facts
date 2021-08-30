package ru.heatalways.chucknorrisfunfacts.presentation.screen.random_joke.select_category

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.toList
import ru.heatalways.chucknorrisfunfacts.domain.interactors.random_joke.select_category.*
import ru.heatalways.chucknorrisfunfacts.presentation.base.BaseMviViewModel
import javax.inject.Inject

@HiltViewModel
class CategorySelectionViewModel @Inject constructor(
    private val categorySelectionInteractor: CategorySelectionInteractor,
    private val savedStateHandle: SavedStateHandle
): BaseMviViewModel<
        CategorySelectionAction,
        CategorySelectionState,
        CategorySelectionEffect,
        CategorySelectionPartialState
>(CategorySelectionStateReducer) {
    override val initialState get() = CategorySelectionState(
        isLoading = true
    )

    init {
        handleSavedState()
        fetchCategories()
    }

    fun handleSavedState() {
        savedStateHandle.get<String?>(SAVED_SEARCH_QUERY)?.let { query ->
            categorySelectionInteractor.fetchCategories()
                .map {
                    categorySelectionInteractor.searchCategories(query).toList().last()
                }
                .onEach { reduceState(it) }
                .launchIn(viewModelScope)
        }
    }

    fun fetchCategories() {
        categorySelectionInteractor.fetchCategories()
            .onEach { reduceState(it) }
            .launchIn(viewModelScope)
    }

    override fun handleAction(action: CategorySelectionAction) {
        when (action) {
            is CategorySelectionAction.OnCategorySelect -> {
                categorySelectionInteractor.selectCategory(action.category)
                setEffect(CategorySelectionEffect.GoBack)
            }

            is CategorySelectionAction.OnSearchExecute ->
                categorySelectionInteractor.searchCategories(action.query)
                    .onEach {
                        reduceState(it)

                        if (it is CategorySelectionPartialState.Categories)
                            setEffect(CategorySelectionEffect.ScrollUp)
                    }
                    .launchIn(viewModelScope)
                    .also { savedStateHandle.set(SAVED_SEARCH_QUERY, action.query) }
        }
    }

    companion object {
        private const val SAVED_SEARCH_QUERY =
            "screen.random_joke.select_category.search_query"
    }
}