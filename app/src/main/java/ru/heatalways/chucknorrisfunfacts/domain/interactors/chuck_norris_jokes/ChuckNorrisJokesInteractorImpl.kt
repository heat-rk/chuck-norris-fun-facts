package ru.heatalways.chucknorrisfunfacts.domain.interactors.chuck_norris_jokes

import kotlinx.coroutines.flow.flow
import ru.heatalways.chucknorrisfunfacts.R
import ru.heatalways.chucknorrisfunfacts.domain.models.Category
import ru.heatalways.chucknorrisfunfacts.domain.models.ChuckJoke
import ru.heatalways.chucknorrisfunfacts.domain.repositories.chuck_norris_jokes.ChuckNorrisJokesRepository
import ru.heatalways.chucknorrisfunfacts.domain.utils.InteractorEvent
import ru.heatalways.chucknorrisfunfacts.core.models.StringResource
import ru.heatalways.chucknorrisfunfacts.domain.utils.paging.PagingConfig
import ru.heatalways.chucknorrisfunfacts.domain.utils.paging.PagingData
import ru.heatalways.chucknorrisfunfacts.domain.utils.paging.PagingSource
import ru.heatalways.chucknorrisfunfacts.core.models.strRes
import ru.heatalways.chucknorrisfunfacts.extensions.handle
import javax.inject.Inject

class ChuckNorrisJokesInteractorImpl @Inject constructor(
    private val repository: ChuckNorrisJokesRepository
): ChuckNorrisJokesInteractor {
    private val pagingSource = object : PagingSource<ChuckJoke, StringResource>() {
        override val initialPageSize = INITIAL_PAGE_SIZE
        override val pageSize = PAGE_SIZE

        override suspend fun load(
            offset: Int,
            limit: Int
        ): PagingData<ChuckJoke, StringResource> {
            val jokes =  repository.getSavedJokesBy(
                limit = limit,
                offset = offset
            )

            return if (offset == 0 && jokes.isEmpty())
                PagingData.Error(strRes(R.string.random_joke_empty_history))
            else
                PagingData.Success(jokes)
        }
    }

    override fun search(query: String) = flow {
        emit(InteractorEvent.Loading)

        val response = repository.search(query)

        response.handle(
            onFailed = { emit(InteractorEvent.Error(it)) },
            onSuccess = { jokes ->
                if (jokes.isEmpty())
                    emit(InteractorEvent.Error(strRes(R.string.error_not_found)))
                else {
                    emit(InteractorEvent.Success(jokes))
                }
            }
        )
    }

    override fun fetchJokes(pagingConfig: PagingConfig) =
            pagingSource.execute(pagingConfig)

    override fun fetchRandomJoke(category: Category) = flow {
        emit(InteractorEvent.Loading)

        val response = repository.random(
            when (category) {
                is Category.Specific -> category.name
                else -> null
            }
        )

        response.handle(
            onFailed = {
                emit(InteractorEvent.Error(it))
            },
            onSuccess = { joke ->
                repository.saveJoke(joke)
                emit(InteractorEvent.Success(
                    repository.getSavedJokesBy(limit = 1).first()
                ))
            }
        )
    }

    override fun removeSavedJokes() = flow {
        if (repository.getAllSavedJokes().isEmpty()) {
            emit(InteractorEvent.Error(strRes(R.string.no_saved_jokes_error)))
            return@flow
        }

        emit(InteractorEvent.Loading)

        repository.saveJokesToTrash()

        if (repository.removeAllSavedJokes() > 0) {
            emit(InteractorEvent.Success(Unit))
        } else {
            emit(InteractorEvent.Error(strRes(R.string.remove_all_error)))
        }
    }

    override fun restoreTrashJokes() = flow {
        emit(InteractorEvent.Loading)

        val jokes = repository.restoreJokesFromTrash()

        emit(
            if (jokes.isNotEmpty())
                InteractorEvent.Success(jokes)
            else
                InteractorEvent.Error(strRes(R.string.random_joke_empty_history))
        )
    }

    override fun fetchCategories() = flow {
        emit(InteractorEvent.Loading)
        val response = repository.categories()

        response.handle(
            onFailed = {
                emit(InteractorEvent.Error(it))
            },
            onSuccess = { categoriesStrings ->
                emit(InteractorEvent.Success(
                    listOf(Category.Any).plus(categoriesStrings)
                ))
            }
        )
    }

    override fun searchCategories(query: String) = flow {
        emit(InteractorEvent.Loading)

        val response = repository.searchCategories(query)

        response.handle(
            onFailed = { emit(InteractorEvent.Error(it)) },
            onSuccess = { categories ->
                if (categories.isNotEmpty())
                    emit(InteractorEvent.Success(categories))
                else
                    emit(InteractorEvent.Error(strRes(R.string.error_not_found)))
            }
        )
    }

    companion object {
        private const val INITIAL_PAGE_SIZE = 10
        private const val PAGE_SIZE = 5
    }
}