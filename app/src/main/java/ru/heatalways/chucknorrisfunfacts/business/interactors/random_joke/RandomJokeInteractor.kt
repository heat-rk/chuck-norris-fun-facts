package ru.heatalways.chucknorrisfunfacts.business.interactors.random_joke

import kotlinx.coroutines.flow.Flow
import ru.heatalways.chucknorrisfunfacts.business.datasource.network.chuck_norris_jokes.Category

interface RandomJokeInteractor {
    fun fetchJokes(): Flow<RandomJokePartialState>

    fun fetchRandomJoke(category: Category): Flow<RandomJokePartialState>
}