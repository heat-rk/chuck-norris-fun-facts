package ru.heatalways.chucknorrisfunfacts.domain.interactors.random_joke

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import ru.heatalways.chucknorrisfunfacts.R
import ru.heatalways.chucknorrisfunfacts.domain.repositories.chuck_norris_jokes.Category
import ru.heatalways.chucknorrisfunfacts.domain.repositories.chuck_norris_jokes.ChuckJoke
import ru.heatalways.chucknorrisfunfacts.domain.repositories.chuck_norris_jokes.ChuckNorrisJokesRepository
import ru.heatalways.chucknorrisfunfacts.domain.utils.StringResource
import ru.heatalways.chucknorrisfunfacts.domain.utils.paging.PagingConfig
import ru.heatalways.chucknorrisfunfacts.domain.utils.paging.PagingData
import ru.heatalways.chucknorrisfunfacts.domain.utils.paging.PagingEvent
import ru.heatalways.chucknorrisfunfacts.domain.utils.paging.PagingSource
import ru.heatalways.chucknorrisfunfacts.domain.utils.strRes
import ru.heatalways.chucknorrisfunfacts.extensions.handle
import javax.inject.Inject

class RandomJokeInteractorImpl @Inject constructor(
    private val chuckNorrisJokesRepository: ChuckNorrisJokesRepository
): RandomJokeInteractor {

    private val pagingSource = object : PagingSource<ChuckJoke, StringResource>() {
        override val initialPageSize = INITIAL_PAGE_SIZE
        override val pageSize = PAGE_SIZE

        override suspend fun load(
            offset: Int,
            limit: Int
        ): PagingData<ChuckJoke, StringResource> {
            val jokes =  chuckNorrisJokesRepository.getSavedJokesBy(
                limit = limit,
                offset = offset
            )

            return if (offset == 0 && jokes.isEmpty())
                PagingData.Error(strRes(R.string.random_joke_empty_history))
            else
                PagingData.Success(jokes)
        }
    }

    override fun fetchJokes(pagingConfig: PagingConfig): Flow<RandomJokePartialState> =
        flow {
           pagingSource.execute(pagingConfig).collect {
               when (it) {
                   is PagingEvent.LoadError ->
                       emit(RandomJokePartialState.Message(it.error))

                   is PagingEvent.Loaded ->
                       emit(RandomJokePartialState.JokesLoaded(it.items))

                   is PagingEvent.Updating ->
                       emit(RandomJokePartialState.JokesUpdating)

                   is PagingEvent.UpdateError -> Unit

                   is PagingEvent.Updated ->
                       emit(RandomJokePartialState.JokesUpdated(it.items))
               }
           }
        }

    override fun fetchRandomJoke(category: Category): Flow<RandomJokePartialState> =
        flow {
            emit(RandomJokePartialState.JokeLoading)

            val response = chuckNorrisJokesRepository.random(
                when (category) {
                    is Category.Specific -> category.name
                    else -> null
                }
            )

            emit(response.handle(
                onFailed = { RandomJokePartialState.JokeLoadingError(it) },
                onSuccess = { joke ->
                    chuckNorrisJokesRepository.saveJoke(joke)
                    RandomJokePartialState.JokeLoaded(joke)
                }
            ))
        }

    companion object {
        private const val INITIAL_PAGE_SIZE = 10
        private const val PAGE_SIZE = 5
    }
}