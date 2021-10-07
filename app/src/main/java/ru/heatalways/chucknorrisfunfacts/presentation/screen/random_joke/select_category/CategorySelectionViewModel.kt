package ru.heatalways.chucknorrisfunfacts.presentation.screen.random_joke.select_category

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.savedstate.SavedStateRegistryOwner
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.heatalways.chucknorrisfunfacts.domain.interactors.chuck_norris_jokes.ChuckNorrisJokesInteractor
import ru.heatalways.chucknorrisfunfacts.domain.models.Category
import ru.heatalways.chucknorrisfunfacts.domain.utils.InteractorEvent
import ru.heatalways.chucknorrisfunfacts.presentation.base.MviViewModel
import ru.heatalways.chucknorrisfunfacts.presentation.util.ScrollState

class CategorySelectionViewModel @AssistedInject constructor(
    @Assisted("onSelect") private val onSelect: (Category) -> Unit,
    @Assisted("savedState") private val savedStateHandle: SavedStateHandle,
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
        onSelect(category)
        navigator { navigateUp() }
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

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("onSelect") config: (Category) -> Unit,
            @Assisted("savedState") savedStateHandle: SavedStateHandle
        ): CategorySelectionViewModel
    }

    companion object {
        private const val SAVED_SEARCH_QUERY =
            "screen.random_joke.select_category.search_query"

        @Suppress("UNCHECKED_CAST")
        fun provideFactory(
            assistedFactory: Factory,
            owner: SavedStateRegistryOwner,
            defaultArgs: Bundle? = null,
            onSelect: (Category) -> Unit
        ) = object : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
            override fun <T : ViewModel?> create(
                key: String,
                modelClass: Class<T>,
                handle: SavedStateHandle
            ): T {
                return assistedFactory.create(onSelect, handle) as T
            }
        }
    }
}