package ru.heatalways.chucknorrisfunfacts.domain.repositories.chuck_norris_jokes

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import ru.heatalways.chucknorrisfunfacts.data.database.AppDatabase
import ru.heatalways.chucknorrisfunfacts.data.network.chuck_norris_jokes.ChuckNorrisJokesApi
import ru.heatalways.chucknorrisfunfacts.data.network.util.api_response.ApiResponse
import ru.heatalways.chucknorrisfunfacts.mappers.toDomain
import ru.heatalways.chucknorrisfunfacts.mappers.toEntity
import java.util.*

class ChuckNorrisJokesRepositoryImpl(
    private val api: ChuckNorrisJokesApi,
    private val appDatabase: AppDatabase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): ChuckNorrisJokesRepository {

    override suspend fun random(category: String?) = withContext(dispatcher) {
        api.random(category).let { response ->
            if (response.isOk && response.value != null) {
                return@withContext ApiResponse(Response.success(
                    response.value.toDomain()
                ))
            }
            return@withContext ApiResponse(response.error)
        }
    }


    override suspend fun categories(): ApiResponse<List<Category>> = withContext(dispatcher) {
        api.categories().let { response ->
            if (response.isOk && response.value != null) {
                return@withContext ApiResponse(Response.success(
                    response.value.map { Category.Specific(it) }
                ))
            }
            return@withContext ApiResponse(response.error)
        }
    }


    override suspend fun search(query: String) = withContext(dispatcher) {
        api.search(query).let { response ->
            if (response.isOk && response.value != null) {
                return@withContext ApiResponse(Response.success(
                    response.value.result?.map { it.toDomain() }
                ))
            }
            return@withContext ApiResponse(response.error)
        }
    }

    override suspend fun saveJoke(joke: ChuckJoke) = withContext(dispatcher) {
        appDatabase.savedJokesDao().insert(joke.toEntity().copy(
            savedAt = Calendar.getInstance().timeInMillis
        ))
    }

    override suspend fun getSavedJokesBy(
        limit: Int,
        offset: Int
    ) = withContext(dispatcher) {
        return@withContext appDatabase.savedJokesDao()
            .getBy(limit, offset)
            .map { it.toDomain() }
    }

    override suspend fun getAllSavedJokes() = withContext(dispatcher) {
        return@withContext appDatabase.savedJokesDao()
            .getAll()
            .map { it.toDomain() }
    }
}