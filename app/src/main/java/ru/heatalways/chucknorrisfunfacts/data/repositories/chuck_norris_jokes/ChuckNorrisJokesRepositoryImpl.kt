package ru.heatalways.chucknorrisfunfacts.data.repositories.chuck_norris_jokes

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.heatalways.chucknorrisfunfacts.data.database.AppDatabase
import ru.heatalways.chucknorrisfunfacts.data.database.InMemoryDatabase
import ru.heatalways.chucknorrisfunfacts.data.network.chuck_norris_jokes.ChuckNorrisJokesApi
import ru.heatalways.chucknorrisfunfacts.data.network.util.safeApiCall
import ru.heatalways.chucknorrisfunfacts.di.modules.IoDispatcher
import ru.heatalways.chucknorrisfunfacts.domain.models.Category
import ru.heatalways.chucknorrisfunfacts.domain.models.ChuckJoke
import ru.heatalways.chucknorrisfunfacts.domain.repositories.chuck_norris_jokes.ChuckNorrisJokesRepository
import ru.heatalways.chucknorrisfunfacts.domain.utils.CacheData
import ru.heatalways.chucknorrisfunfacts.mappers.toDomain
import ru.heatalways.chucknorrisfunfacts.mappers.toEntity
import java.util.*
import javax.inject.Inject

class ChuckNorrisJokesRepositoryImpl @Inject constructor(
    private val api: ChuckNorrisJokesApi,
    private val appDatabase: AppDatabase,
    private val inMemoryDatabase: InMemoryDatabase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
): ChuckNorrisJokesRepository {
    private var categoriesCache = CacheData<List<Category>>()

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
                if (it.savedAt != null)
                    it.savedAt.time
                else
                    Calendar.getInstance().timeInMillis
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

    override suspend fun saveJokesToTrash() {
        inMemoryDatabase.trashJokesDao().apply {
            clear()
            insert(appDatabase.savedJokesDao().getAll())
        }
    }

    override suspend fun restoreJokesFromTrash(): List<ChuckJoke> {
        appDatabase.savedJokesDao()
            .insert(inMemoryDatabase.trashJokesDao().getAll())

        inMemoryDatabase.trashJokesDao()
            .clear()

        return appDatabase.savedJokesDao()
            .getAll().map { it.toDomain() }
    }
}