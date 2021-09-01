package ru.heatalways.chucknorrisfunfacts.domain.interactors.random_joke

import kotlinx.coroutines.flow.Flow
import ru.heatalways.chucknorrisfunfacts.domain.repositories.chuck_norris_jokes.Category
import ru.heatalways.chucknorrisfunfacts.domain.utils.paging.PagingConfig

interface RandomJokeInteractor {
    fun fetchJokes(pagingConfig: PagingConfig): Flow<RandomJokePartialState>
    fun fetchRandomJoke(category: Category): Flow<RandomJokePartialState>
}