package ru.heatalways.chucknorrisfunfacts.domain.interactors.random_joke

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.heatalways.chucknorrisfunfacts.R
import ru.heatalways.chucknorrisfunfacts.domain.repositories.chuck_norris_jokes.Category
import ru.heatalways.chucknorrisfunfacts.domain.repositories.chuck_norris_jokes.ChuckNorrisJokesRepository
import ru.heatalways.chucknorrisfunfacts.domain.utils.strRes

class RandomJokeInteractorImpl(
    private val chuckNorrisJokesRepository: ChuckNorrisJokesRepository
): RandomJokeInteractor {

    override fun fetchJokes(): Flow<RandomJokePartialState> = flow {
        val jokes = chuckNorrisJokesRepository.getAllSavedJokes()

        if (jokes.isNotEmpty())
            emit(RandomJokePartialState.JokesLoaded(jokes))
        else
            emit(RandomJokePartialState.Message(strRes(R.string.random_joke_empty_history)))
    }

    override fun fetchRandomJoke(category: Category): Flow<RandomJokePartialState> = flow {
        emit(RandomJokePartialState.JokeLoading)

        val response = chuckNorrisJokesRepository.random(
            when (category) {
                is Category.Specific -> category.name
                else -> null
            }
        )

        if (response.isOk && response.value != null) {
            chuckNorrisJokesRepository.saveJoke(response.value)
            emit(RandomJokePartialState.JokeLoaded(response.value))
        } else {
            //setEffect(RandomJokeViewEffect.Error(response.error?.message))
        }
    }
}