package ru.heatalways.chucknorrisfunfacts.domain.interactors.search_joke

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.heatalways.chucknorrisfunfacts.R
import ru.heatalways.chucknorrisfunfacts.data.utils.strRes
import ru.heatalways.chucknorrisfunfacts.domain.managers.chuck_norris_jokes.ChuckNorrisJokesManager
import javax.inject.Inject
import javax.inject.Singleton

interface SearchJokeInteractor{
    fun search(query: String): Flow<SearchJokePartialState>
}