package ru.heatalways.chucknorrisfunfacts.domain.managers.chuck_norris_jokes

import retrofit2.Response
import ru.heatalways.chucknorrisfunfacts.data.entities.ChuckJoke
import ru.heatalways.chucknorrisfunfacts.domain.database.AppDatabase
import ru.heatalways.chucknorrisfunfacts.domain.network.api.ChuckNorrisJokesApi
import ru.heatalways.chucknorrisfunfacts.domain.network.api_response.ApiResponse

class ChuckNorrisJokesManagerImpl(
    private val api: ChuckNorrisJokesApi,
    private val appDatabase: AppDatabase
): ChuckNorrisJokesManager {

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
        appDatabase.savedJokesDao().insert(joke)
    }

    override suspend fun getSavedJokesBy(limit: Int, offset: Int): List<ChuckJoke> {
        return appDatabase.savedJokesDao().getBy(limit, offset)
    }

    override suspend fun getAllSavedJokes(): List<ChuckJoke> {
        return appDatabase.savedJokesDao().getAll()
    }
}