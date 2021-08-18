package ru.heatalways.chucknorrisfunfacts.business.interactors.search_joke

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.heatalways.chucknorrisfunfacts.R
import ru.heatalways.chucknorrisfunfacts.business.domain.utils.strRes
import ru.heatalways.chucknorrisfunfacts.business.domain.repositories.chuck_norris_jokes.ChuckNorrisJokesRepository

class SearchJokeInteractorImpl(
    val chuckNorrisJokesRepository: ChuckNorrisJokesRepository
): SearchJokeInteractor {
    override fun search(query: String): Flow<SearchJokePartialState> = flow {
        emit(SearchJokePartialState.Loading)
        val response = chuckNorrisJokesRepository.search(query)

        when {
            !response.isOk || response.value == null ->
                emit(SearchJokePartialState.Message(strRes(response.error?.message)))

            response.value.isEmpty() ->
                emit(SearchJokePartialState.Message(strRes(R.string.error_not_found)))

            else ->
                emit(SearchJokePartialState.Jokes(response.value))
        }
    }
}