package ru.heatalways.chucknorrisfunfacts.presentation.screen.random_joke.select_category

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.heatalways.chucknorrisfunfacts.domain.interactors.random_joke.select_category.*
import ru.heatalways.chucknorrisfunfacts.presentation.base.BaseMviViewModel
import javax.inject.Inject

@HiltViewModel
class CategorySelectionViewModel @Inject constructor(
    private val categorySelectionInteractor: CategorySelectionInteractor
): BaseMviViewModel<
        CategorySelectionAction,
        CategorySelectionState,
        CategorySelectionEffect,
        CategorySelectionPartialState
>(CategorySelectionStateReducer) {
    override val initialState get() = CategorySelectionState(
        isLoading = true
    )

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

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
                    .onEach { reduceState(it) }
                    .launchIn(viewModelScope)
        }
    }
}