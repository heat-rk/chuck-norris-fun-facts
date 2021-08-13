package ru.heatalways.chucknorrisfunfacts.presentation.screen.random_joke

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.heatalways.chucknorrisfunfacts.R
import ru.heatalways.chucknorrisfunfacts.data.entities.Category
import ru.heatalways.chucknorrisfunfacts.domain.managers.chuck_norris_jokes.ChuckNorrisJokesManager
import ru.heatalways.chucknorrisfunfacts.presentation.base.BaseMviViewModel
import javax.inject.Inject

@HiltViewModel
class RandomJokeViewModel @Inject constructor(
    private val jokesManager: ChuckNorrisJokesManager
): BaseMviViewModel<
        RandomJokeContract.Action,
        RandomJokeContract.State,
        RandomJokeContract.Effect,
        RandomJokeContract.PartialState
>(RandomJokeContract.Reducer) {

    override val initialState get() = RandomJokeContract.State(isLoading = true)

    init {
        fetchJokes()
    }

    override fun handleAction(action: RandomJokeContract.Action) {
        when (action) {
            is RandomJokeContract.Action.OnCategorySelectionButtonClick -> {
                setEffect(RandomJokeContract.Effect.NavigateToCategorySelectionScreen)
            }

            is RandomJokeContract.Action.OnRandomJokeRequest -> {
                fetchRandomJoke(action.category)
            }
        }
    }

    private fun fetchJokes() {
        viewModelScope.launch {
            val jokes = jokesManager.getAllSavedJokes()

            if (jokes.isNotEmpty())
                reduceState(RandomJokeContract.PartialState.JokesLoaded(jokes))
            else
                reduceState(RandomJokeContract.PartialState.Message(
                    strRes(R.string.random_joke_empty_history)
                ))
        }
    }

    private fun fetchRandomJoke(selectedCategory: Category) {
        viewModelScope.launch {
            reduceState(RandomJokeContract.PartialState.JokeLoading)

            val response = jokesManager.random(
                when (selectedCategory) {
                    is Category.Specific -> selectedCategory.name
                    else -> null
                }
            )

            if (response.isOk && response.value != null) {
                jokesManager.saveJoke(response.value)
                reduceState(RandomJokeContract.PartialState.JokeLoaded(response.value))
            } else {
                setEffect(RandomJokeContract.Effect.Error(response.error?.message))
            }
        }
    }
}