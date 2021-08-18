package ru.heatalways.chucknorrisfunfacts.business.domain.repositories.chuck_norris_jokes

import ru.heatalways.chucknorrisfunfacts.business.datasource.network.chuck_norris_jokes.ChuckJoke
import ru.heatalways.chucknorrisfunfacts.business.datasource.network.util.api_response.ApiResponse

interface ChuckNorrisJokesRepository {
    suspend fun random(category: String? = null): ApiResponse<ChuckJoke>
    suspend fun categories(): ApiResponse<List<String>>
    suspend fun search(query: String): ApiResponse<List<ChuckJoke>>

    suspend fun saveJoke(joke: ChuckJoke)
    suspend fun getSavedJokesBy(limit: Int = 10, offset: Int = 0): List<ChuckJoke>
    suspend fun getAllSavedJokes(): List<ChuckJoke>
}