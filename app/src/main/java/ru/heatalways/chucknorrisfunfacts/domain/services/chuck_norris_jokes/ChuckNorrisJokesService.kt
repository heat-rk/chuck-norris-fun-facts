package ru.heatalways.chucknorrisfunfacts.domain.services.chuck_norris_jokes

import ru.heatalways.chucknorrisfunfacts.data.entities.ChuckJoke
import ru.heatalways.chucknorrisfunfacts.domain.network.api_response.ApiResponse

interface ChuckNorrisJokesService {
    suspend fun random(category: String? = null): ApiResponse<ChuckJoke>
    suspend fun categories(): ApiResponse<List<String>>
    suspend fun search(query: String): ApiResponse<List<ChuckJoke>>
}