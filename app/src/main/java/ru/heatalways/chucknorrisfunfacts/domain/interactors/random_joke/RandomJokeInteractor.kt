package ru.heatalways.chucknorrisfunfacts.domain.interactors.random_joke

import kotlinx.coroutines.flow.Flow
import ru.heatalways.chucknorrisfunfacts.data.entities.Category

interface RandomJokeInteractor {
    fun fetchJokes(): Flow<RandomJokePartialState>

    fun fetchRandomJoke(category: Category): Flow<RandomJokePartialState>
}