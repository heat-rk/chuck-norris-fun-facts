package ru.heatalways.chucknorrisfunfacts.domain.managers

import retrofit2.Response
import ru.heatalways.chucknorrisfunfacts.data.entities.ChuckJoke
import ru.heatalways.chucknorrisfunfacts.domain.managers.chuck_norris_jokes.ChuckNorrisJokesManager
import ru.heatalways.chucknorrisfunfacts.domain.network.api_response.ApiError
import ru.heatalways.chucknorrisfunfacts.domain.network.api_response.ApiResponse
import java.util.*

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
            value = "that joke said hey",
            savedAt = null
        ),
        ChuckJoke(
            categories = listOf(categories[0], categories[1]),
            createdAt = null,
            iconUrl = null,
            id = "2",
            updatedAt = null,
            url = null,
            value = "never gonna give you up",
            savedAt = null
        ),
        ChuckJoke(
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

    private val savedJokes = mutableListOf<ChuckJoke>()

    var shouldReturnErrorResponse = false

    override suspend fun random(category: String?): ApiResponse<ChuckJoke> {
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

    override suspend fun search(query: String): ApiResponse<List<ChuckJoke>> {
        return if (shouldReturnErrorResponse) {
            ApiResponse(ApiError.ServerUnknownError(404))
        } else {
            ApiResponse(Response.success(jokes.filter { it.value?.contains(query) ?: false }))
        }
    }

    override suspend fun saveJoke(joke: ChuckJoke) {
        savedJokes.add(joke.apply {
            savedAt = Calendar.getInstance().timeInMillis
        })
    }

    override suspend fun getSavedJokesBy(limit: Int, offset: Int): List<ChuckJoke> {
        val from = offset
        val to = from + limit

        if (from > savedJokes.lastIndex)
            return emptyList()

        return savedJokes.subList(
            fromIndex = from,
            toIndex = minOf(to, savedJokes.size)
        ).sortedByDescending { it.savedAt }
    }

    override suspend fun getAllSavedJokes(): List<ChuckJoke> {
        return savedJokes.sortedByDescending { it.savedAt }
    }
}