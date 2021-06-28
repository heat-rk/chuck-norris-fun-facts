package ru.heatalways.chucknorrisfunfacts.domain.managers.chuck_norris_jokes

import ru.heatalways.chucknorrisfunfacts.data.entities.ChuckJoke
import ru.heatalways.chucknorrisfunfacts.domain.network.api_response.ApiResponse

interface ChuckNorrisJokesManager {
    suspend fun random(category: String? = null): ApiResponse<ChuckJoke>
    suspend fun categories(): ApiResponse<List<String>>
    suspend fun search(query: String): ApiResponse<List<ChuckJoke>>

    suspend fun saveJoke(joke: ChuckJoke)
    suspend fun getSavedJokesBy(limit: Int = 10, offset: Int = 0): List<ChuckJoke>
    suspend fun getAllSavedJokes(): List<ChuckJoke>
}