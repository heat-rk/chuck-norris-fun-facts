package ru.heatalways.chucknorrisfunfacts.domain.repositories.chuck_norris_jokes

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.heatalways.chucknorrisfunfacts.data.database.AppDatabase
import ru.heatalways.chucknorrisfunfacts.data.network.chuck_norris_jokes.ChuckNorrisJokesApi
import ru.heatalways.chucknorrisfunfacts.data.network.util.safeApiCall
import ru.heatalways.chucknorrisfunfacts.mappers.toDomain
import ru.heatalways.chucknorrisfunfacts.mappers.toEntity
import java.util.*

class ChuckNorrisJokesRepositoryImpl(
    private val api: ChuckNorrisJokesApi,
    private val appDatabase: AppDatabase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): ChuckNorrisJokesRepository {

    override suspend fun random(category: String?) = safeApiCall(dispatcher) {
        api.random(category).toDomain()
    }

    override suspend fun categories() = safeApiCall(dispatcher) {
        api.categories().map { Category.Specific(it) }
    }

    override suspend fun search(query: String) = safeApiCall(dispatcher) {
        api.search(query).result?.map { it.toDomain() } ?: emptyList()
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
        appDatabase.savedJokesDao()
            .getBy(limit, offset)
            .map { it.toDomain() }
    }

    override suspend fun getAllSavedJokes() = withContext(dispatcher) {
        appDatabase.savedJokesDao()
            .getAll()
            .map { it.toDomain() }
    }
}