package ru.heatalways.chucknorrisfunfacts.presentation.screen.random_joke

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.heatalways.chucknorrisfunfacts.domain.interactors.random_joke.*
import ru.heatalways.chucknorrisfunfacts.domain.interactors.random_joke.select_category.CategorySelectionInteractor
import ru.heatalways.chucknorrisfunfacts.domain.repositories.chuck_norris_jokes.Category
import ru.heatalways.chucknorrisfunfacts.presentation.base.BaseMviViewModel
import javax.inject.Inject

@HiltViewModel
class RandomJokeViewModel @Inject constructor(
    private val randomJokeInteractor: RandomJokeInteractor,
    private val categorySelectionInteractor: CategorySelectionInteractor,
    private val savedStateHandle: SavedStateHandle
): BaseMviViewModel<
        RandomJokeAction,
        RandomJokeViewState,
        RandomJokeViewEffect,
        RandomJokePartialState
>(RandomJokeStateReducer) {

    override val initialState get() = RandomJokeViewState(isLoading = true)

    init {
        handleSavedState()
        collectSelectedCategory()
        fetchJokes()
    }

    fun handleSavedState() {
        savedStateHandle.get<Category?>(SAVED_SELECTED_CATEGORY)?.let { category ->
            categorySelectionInteractor.selectCategory(category)
        }
    }

    fun collectSelectedCategory() {
        categorySelectionInteractor.selectedCategory
            .onEach {
                reduceState(RandomJokePartialState.CategorySelected(it))
                savedStateHandle.set(SAVED_SELECTED_CATEGORY, it)
            }
            .launchIn(viewModelScope)
    }

    fun fetchJokes() {
        randomJokeInteractor.fetchJokes()
            .onEach { reduceState(it) }
            .launchIn(viewModelScope)
    }

    override fun handleAction(action: RandomJokeAction) {
        when (action) {
            is RandomJokeAction.OnCategorySelectionButtonClick -> {
                setEffect(RandomJokeViewEffect.NavigateToCategorySelectionScreen)
            }

            is RandomJokeAction.OnRandomJokeRequest ->
                randomJokeInteractor.fetchRandomJoke(
                    categorySelectionInteractor.selectedCategory.value
                )
                    .onEach {
                        if (it is RandomJokePartialState.JokeLoadingError)
                            setEffect(RandomJokeViewEffect.Error(it.message))

                        reduceState(it)

                        if (it is RandomJokePartialState.JokeLoaded)
                            setEffect(RandomJokeViewEffect.ScrollUp)
                    }
                    .launchIn(viewModelScope)
        }
    }

    companion object {
        private const val SAVED_SELECTED_CATEGORY =
            "screen.random_joke.saved_selected_category"
    }
}