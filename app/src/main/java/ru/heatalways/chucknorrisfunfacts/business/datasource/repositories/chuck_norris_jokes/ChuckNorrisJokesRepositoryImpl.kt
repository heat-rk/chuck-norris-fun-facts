package ru.heatalways.chucknorrisfunfacts.business.datasource.repositories.chuck_norris_jokes

import retrofit2.Response
import ru.heatalways.chucknorrisfunfacts.business.datasource.network.chuck_norris_jokes.ChuckJoke
import ru.heatalways.chucknorrisfunfacts.business.datasource.database.AppDatabase
import ru.heatalways.chucknorrisfunfacts.business.datasource.network.chuck_norris_jokes.ChuckNorrisJokesApi
import ru.heatalways.chucknorrisfunfacts.business.datasource.network.util.api_response.ApiResponse
import ru.heatalways.chucknorrisfunfacts.business.domain.repositories.chuck_norris_jokes.ChuckNorrisJokesRepository
import java.util.*

class ChuckNorrisJokesRepositoryImpl(
    private val api: ChuckNorrisJokesApi,
    private val appDatabase: AppDatabase
): ChuckNorrisJokesRepository {

    override suspend fun random(category: String?): ApiResponse<ChuckJoke> {
        api.random(category).let { response ->
            if (response.isOk && response.value != null) {
                return ApiResponse(Response.success(response.value))
            }
            return ApiResponse(response.error)
        }
    }

    override suspend fun categories(): ApiResponse<List<String>> {
        api.categories().let { response ->
            if (response.isOk && response.value != null) {
                return ApiResponse(Response.success(response.value))
            }
            return ApiResponse(response.error)
        }
    }

    override suspend fun search(query: String): ApiResponse<List<ChuckJoke>> {
        api.search(query).let { response ->
            if (response.isOk && response.value != null) {
                return ApiResponse(Response.success(response.value.result))
            }
            return ApiResponse(response.error)
        }
    }

    override suspend fun saveJoke(joke: ChuckJoke) {
        appDatabase.savedJokesDao().insert(joke.apply {
            savedAt = Calendar.getInstance().timeInMillis
        })
    }

    override suspend fun getSavedJokesBy(limit: Int, offset: Int): List<ChuckJoke> {
        return appDatabase.savedJokesDao().getBy(limit, offset)
    }

    override suspend fun getAllSavedJokes(): List<ChuckJoke> {
        return appDatabase.savedJokesDao().getAll()
    }
}