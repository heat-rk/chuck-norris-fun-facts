package ru.heatalways.chucknorrisfunfacts.business.datasource.repositories.chuck_norris_jokes

import retrofit2.Response
import ru.heatalways.chucknorrisfunfacts.business.datasource.network.chuck_norris_jokes.ChuckJokeNetwork
import ru.heatalways.chucknorrisfunfacts.business.domain.repositories.chuck_norris_jokes.ChuckNorrisJokesRepository
import ru.heatalways.chucknorrisfunfacts.business.datasource.network.util.api_response.ApiError
import ru.heatalways.chucknorrisfunfacts.business.datasource.network.util.api_response.ApiResponse
import java.util.*

class ChuckNorrisJokesRepositoryFake: ChuckNorrisJokesRepository {
    private val categories = listOf("animal", "career")

    private val jokes = listOf(
        ChuckJokeNetwork(
            categories = emptyList(),
            createdAt = null,
            iconUrl = null,
            id = "1",
            updatedAt = null,
            url = null,
            value = "that joke said hey",
            savedAt = null
        ),
        ChuckJokeNetwork(
            categories = listOf(categories[0], categories[1]),
            createdAt = null,
            iconUrl = null,
            id = "2",
            updatedAt = null,
            url = null,
            value = "never gonna give you up",
            savedAt = null
        ),
        ChuckJokeNetwork(
            categories = listOf(categories[0]),
            createdAt = null,
            iconUrl = null,
            id = "3",
            updatedAt = null,
            url = null,
            value = "wrong side of heaven",
            savedAt = null
        )
    )

    private val savedJokes = mutableListOf<ChuckJokeNetwork>()

    var shouldReturnErrorResponse = false

    override suspend fun random(category: String?): ApiResponse<ChuckJokeNetwork> {
        return if (shouldReturnErrorResponse) {
            ApiResponse(ApiError.ServerUnknownError(404))
        } else {
            if (category == null) ApiResponse(Response.success(jokes.random()))
            else ApiResponse(Response.success(
                jokes.filter { it.categories?.contains(category) == true }.random()
            ))
        }
    }

    override suspend fun categories(): ApiResponse<List<String>> {
        return if (shouldReturnErrorResponse) {
            ApiResponse(ApiError.ServerUnknownError(404))
        } else {
            ApiResponse(Response.success(categories))
        }
    }

    override suspend fun search(query: String): ApiResponse<List<ChuckJokeNetwork>> {
        return if (shouldReturnErrorResponse) {
            ApiResponse(ApiError.ServerUnknownError(404))
        } else {
            ApiResponse(Response.success(jokes.filter { it.value?.contains(query) ?: false }))
        }
    }

    override suspend fun saveJoke(joke: ChuckJokeNetwork) {
        savedJokes.add(joke.apply {
            savedAt = Calendar.getInstance().timeInMillis
        })
    }

    override suspend fun getSavedJokesBy(limit: Int, offset: Int): List<ChuckJokeNetwork> {
        val from = offset
        val to = from + limit

        if (from > savedJokes.lastIndex)
            return emptyList()

        return savedJokes.subList(
            fromIndex = from,
            toIndex = minOf(to, savedJokes.size)
        ).sortedByDescending { it.savedAt }
    }

    override suspend fun getAllSavedJokes(): List<ChuckJokeNetwork> {
        return savedJokes.sortedByDescending { it.savedAt }
    }
}