package ru.heatalways.chucknorrisfunfacts.business.interactors.search_joke

import kotlinx.coroutines.flow.Flow

interface SearchJokeInteractor{
    fun search(query: String): Flow<SearchJokePartialState>
}