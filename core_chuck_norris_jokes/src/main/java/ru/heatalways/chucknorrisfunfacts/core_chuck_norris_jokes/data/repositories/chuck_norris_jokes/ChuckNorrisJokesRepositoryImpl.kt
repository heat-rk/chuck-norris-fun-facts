package ru.heatalways.chucknorrisfunfacts.core_chuck_norris_jokes.data.repositories.chuck_norris_jokes

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.heatalways.chucknorrisfunfacts.core.data.database.AppDatabase
import ru.heatalways.chucknorrisfunfacts.core.data.database.models.ChuckJokeEntity
import ru.heatalways.chucknorrisfunfacts.core.domain.models.Category
import ru.heatalways.chucknorrisfunfacts.core.domain.models.ChuckJoke
import ru.heatalways.chucknorrisfunfacts.core_chuck_norris_jokes.data.network.chuck_norris_jokes.ChuckNorrisJokesApi
import ru.heatalways.chucknorrisfunfacts.core_chuck_norris_jokes.domain.repositories.chuck_norris_jokes.ChuckNorrisJokesRepository
import ru.heatalways.chucknorrisfunfacts.core_chuck_norris_jokes.mappers.toDomain
import ru.heatalways.chucknorrisfunfacts.core_chuck_norris_jokes.mappers.toEntity
import ru.heatalways.chucknorrisfunfacts.common.data.utils.CacheData
import ru.heatalways.chucknorrisfunfacts.common.data.utils.safeApiCall
import ru.heatalways.chucknorrisfunfacts.common.di.IoDispatcher
import java.util.*
import javax.inject.Inject

internal class ChuckNorrisJokesRepositoryImpl @Inject constructor(
    private val api: ChuckNorrisJokesApi,
    private val appDatabase: AppDatabase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
): ChuckNorrisJokesRepository {
    private val categoriesCache = CacheData<List<Category>>()
    private val trashJokesCache = CacheData<List<ChuckJokeEntity>>()

    override suspend fun random(category: String?) = safeApiCall(dispatcher) {
        api.random(category).toDomain()
    }

    override suspend fun categories() = safeApiCall(dispatcher) {
        categoriesCache.getValue() ?: api.categories()
            .map { Category.Specific(it) }
            .also { categoriesCache.setValue(it) }
    }

    override suspend fun searchCategories(query: String) = safeApiCall(dispatcher) {
        val categories = categoriesCache.getValue() ?: api.categories()
            .map { Category.Specific(it) }
            .also { categoriesCache.setValue(it) }

        if (query.isEmpty()) categories
        else categories.filter { it is Category.Specific && it.name.contains(query) }
    }

    override suspend fun search(query: String) = safeApiCall(dispatcher) {
        api.search(query).result?.map { it.toDomain() } ?: emptyList()
    }

    override suspend fun saveJoke(joke: ChuckJoke) = withContext(dispatcher) {
        appDatabase.savedJokesDao().insert(joke.toEntity().copy(
            savedAt = Calendar.getInstance().timeInMillis
        ))
    }

    override suspend fun saveJokes(jokes: List<ChuckJoke>) = withContext(dispatcher) {
        appDatabase.savedJokesDao().insert(jokes.map { it.toEntity().copy(
            savedAt =
                it.savedAt?.time ?: Calendar.getInstance().timeInMillis
        ) })
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

    override suspend fun removeAllSavedJokes() = withContext(dispatcher) {
        appDatabase.savedJokesDao()
            .clear()
    }

    override suspend fun saveJokesToTrash() = withContext(dispatcher) {
        trashJokesCache.setValue(
            appDatabase.savedJokesDao().getAll()
        )
    }

    override suspend fun restoreJokesFromTrash() = withContext(dispatcher) {
        trashJokesCache.getValue()?.let { jokes ->
            appDatabase.savedJokesDao().insert(jokes)
            appDatabase.savedJokesDao().getAll().map { it.toDomain() }
        } ?: emptyList()
    }
}