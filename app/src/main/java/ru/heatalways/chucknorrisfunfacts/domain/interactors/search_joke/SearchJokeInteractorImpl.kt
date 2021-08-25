package ru.heatalways.chucknorrisfunfacts.domain.interactors.search_joke

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.heatalways.chucknorrisfunfacts.R
import ru.heatalways.chucknorrisfunfacts.extensions.handle
import ru.heatalways.chucknorrisfunfacts.domain.repositories.chuck_norris_jokes.ChuckNorrisJokesRepository
import ru.heatalways.chucknorrisfunfacts.domain.utils.strRes

class SearchJokeInteractorImpl(
    private val chuckNorrisJokesRepository: ChuckNorrisJokesRepository
): SearchJokeInteractor {
    override fun search(query: String): Flow<SearchJokePartialState> = flow {
        emit(SearchJokePartialState.Loading)
        val response = chuckNorrisJokesRepository.search(query)

        emit(response.handle(
            onFailed = { SearchJokePartialState.Message(it) },
            onSuccess = { jokes ->
                if (jokes.isEmpty())
                    SearchJokePartialState.Message(strRes(R.string.error_not_found))
                else
                    SearchJokePartialState.Jokes(jokes)
            }
        ))
    }
}