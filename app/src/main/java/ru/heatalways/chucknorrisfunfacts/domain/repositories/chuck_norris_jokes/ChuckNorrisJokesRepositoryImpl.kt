package ru.heatalways.chucknorrisfunfacts.domain.repositories.chuck_norris_jokes

import retrofit2.Response
import ru.heatalways.chucknorrisfunfacts.data.database.AppDatabase
import ru.heatalways.chucknorrisfunfacts.data.network.chuck_norris_jokes.ChuckNorrisJokesApi
import ru.heatalways.chucknorrisfunfacts.data.network.util.api_response.ApiResponse
import ru.heatalways.chucknorrisfunfacts.mappers.toDomain
import ru.heatalways.chucknorrisfunfacts.mappers.toEntity
import java.util.*

class ChuckNorrisJokesRepositoryImpl(
    private val api: ChuckNorrisJokesApi,
    private val appDatabase: AppDatabase
): ChuckNorrisJokesRepository {

    override suspend fun random(category: String?): ApiResponse<ChuckJoke> {
        api.random(category).let { response ->
            if (response.isOk && response.value != null) {
                return ApiResponse(Response.success(
                    response.value.toDomain()
                ))
            }
            return ApiResponse(response.error)
        }
    }

    override suspend fun categories(): ApiResponse<List<Category>> {
        api.categories().let { response ->
            if (response.isOk && response.value != null) {
                return ApiResponse(Response.success(
                    response.value.map { Category.Specific(it) }
                ))
            }
            return ApiResponse(response.error)
        }
    }

    override suspend fun search(query: String): ApiResponse<List<ChuckJoke>> {
        api.search(query).let { response ->
            if (response.isOk && response.value != null) {
                return ApiResponse(Response.success(
                    response.value.result?.map { it.toDomain() }
                ))
            }
            return ApiResponse(response.error)
        }
    }

    override suspend fun saveJoke(joke: ChuckJoke) {
        appDatabase.savedJokesDao().insert(joke.toEntity().copy(
            savedAt = Calendar.getInstance().timeInMillis
        ))
    }

    override suspend fun getSavedJokesBy(limit: Int, offset: Int): List<ChuckJoke> {
        return appDatabase.savedJokesDao()
            .getBy(limit, offset)
            .map { it.toDomain() }
    }

    override suspend fun getAllSavedJokes(): List<ChuckJoke> {
        return appDatabase.savedJokesDao()
            .getAll()
            .map { it.toDomain() }
    }
}