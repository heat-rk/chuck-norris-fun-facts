package ru.heatalways.chucknorrisfunfacts.domain.interactors.search_joke

import kotlinx.coroutines.flow.Flow

interface SearchJokeInteractor{
    fun search(query: String): Flow<SearchJokePartialState>
}