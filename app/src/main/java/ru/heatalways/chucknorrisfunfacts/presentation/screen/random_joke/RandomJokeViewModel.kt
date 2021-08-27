package ru.heatalways.chucknorrisfunfacts.presentation.screen.random_joke

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.heatalways.chucknorrisfunfacts.domain.interactors.random_joke.*
import ru.heatalways.chucknorrisfunfacts.domain.interactors.random_joke.select_category.CategorySelectionInteractor
import ru.heatalways.chucknorrisfunfacts.presentation.base.BaseMviViewModel
import javax.inject.Inject

@HiltViewModel
class RandomJokeViewModel @Inject constructor(
    private val randomJokeInteractor: RandomJokeInteractor,
    private val categorySelectionInteractor: CategorySelectionInteractor
): BaseMviViewModel<
        RandomJokeAction,
        RandomJokeViewState,
        RandomJokeViewEffect,
        RandomJokePartialState
>(RandomJokeStateReducer) {

    override val initialState get() = RandomJokeViewState(isLoading = true)

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        randomJokeInteractor.fetchJokes()
            .onEach { reduceState(it) }
            .launchIn(viewModelScope)

        categorySelectionInteractor.selectedCategory
            .onEach { reduceState(RandomJokePartialState.CategorySelected(it)) }
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
                    }
                    .launchIn(viewModelScope)
        }
    }
}