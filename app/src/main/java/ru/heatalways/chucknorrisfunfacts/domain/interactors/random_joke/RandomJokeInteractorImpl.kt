package ru.heatalways.chucknorrisfunfacts.domain.interactors.random_joke

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.heatalways.chucknorrisfunfacts.R
import ru.heatalways.chucknorrisfunfacts.data.entities.Category
import ru.heatalways.chucknorrisfunfacts.data.utils.strRes
import ru.heatalways.chucknorrisfunfacts.domain.interactors.random_joke.select_category.CategorySelectionInteractor
import ru.heatalways.chucknorrisfunfacts.domain.managers.chuck_norris_jokes.ChuckNorrisJokesManager

class RandomJokeInteractorImpl(
    private val chuckNorrisJokesManager: ChuckNorrisJokesManager
): RandomJokeInteractor {

    override fun fetchJokes(): Flow<RandomJokePartialState> = flow {
        val jokes = chuckNorrisJokesManager.getAllSavedJokes()

        if (jokes.isNotEmpty())
            emit(RandomJokePartialState.JokesLoaded(jokes))
        else
            emit(RandomJokePartialState.Message(strRes(R.string.random_joke_empty_history)))
    }

    override fun fetchRandomJoke(category: Category): Flow<RandomJokePartialState> = flow {
        emit(RandomJokePartialState.JokeLoading)

        val response = chuckNorrisJokesManager.random(
            when (category) {
                is Category.Specific -> category.name
                else -> null
            }
        )

        if (response.isOk && response.value != null) {
            chuckNorrisJokesManager.saveJoke(response.value)
            emit(RandomJokePartialState.JokeLoaded(response.value))
        } else {
            //setEffect(RandomJokeViewEffect.Error(response.error?.message))
        }
    }
}