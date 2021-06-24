package ru.heatalways.chucknorrisfunfacts.domain.managers

import retrofit2.Response
import ru.heatalways.chucknorrisfunfacts.data.entities.ChuckJoke
import ru.heatalways.chucknorrisfunfacts.domain.managers.chuck_norris_jokes.ChuckNorrisJokesManager
import ru.heatalways.chucknorrisfunfacts.domain.network.api_response.ApiError
import ru.heatalways.chucknorrisfunfacts.domain.network.api_response.ApiResponse

class ChuckNorrisJokesManagerFake: ChuckNorrisJokesManager {
    private val categories = listOf("animal", "career")

    private val jokes = listOf(
        ChuckJoke(
            categories = emptyList(),
            createdAt = null,
            iconUrl = null,
            id = "1",
            updatedAt = null,
            url = null,
            value = "that joke said hey"
        ),
        ChuckJoke(
            categories = listOf(categories[0], categories[1]),
            createdAt = null,
            iconUrl = null,
            id = "2",
            updatedAt = null,
            url = null,
            value = "never gonna give you up"
        ),
        ChuckJoke(
            categories = listOf(categories[0]),
            createdAt = null,
            iconUrl = null,
            id = "3",
            updatedAt = null,
            url = null,
            value = "wrong side of heaven"
        ),
    )

    var shouldReturnErrorResponse = false

    override suspend fun random(category: String?): ApiResponse<ChuckJoke> {
        return if (shouldReturnErrorResponse) {
            ApiResponse(ApiError.ServerUnknownError(404))
        } else {
            ApiResponse(Response.success(jokes.random()))
        }
    }

    override suspend fun categories(): ApiResponse<List<String>> {
        return if (shouldReturnErrorResponse) {
            ApiResponse(ApiError.ServerUnknownError(404))
        } else {
            ApiResponse(Response.success(categories))
        }
    }

    override suspend fun search(query: String): ApiResponse<List<ChuckJoke>> {
        return if (shouldReturnErrorResponse) {
            ApiResponse(ApiError.ServerUnknownError(404))
        } else {
            ApiResponse(Response.success(jokes.filter { it.value?.contains(query) ?: false }))
        }
    }
}