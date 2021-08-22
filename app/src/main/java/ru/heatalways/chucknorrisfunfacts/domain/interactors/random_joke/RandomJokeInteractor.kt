package ru.heatalways.chucknorrisfunfacts.domain.interactors.random_joke

import kotlinx.coroutines.flow.Flow
import ru.heatalways.chucknorrisfunfacts.domain.repositories.chuck_norris_jokes.Category

interface RandomJokeInteractor {
    fun fetchJokes(): Flow<RandomJokePartialState>

    fun fetchRandomJoke(category: Category): Flow<RandomJokePartialState>
}