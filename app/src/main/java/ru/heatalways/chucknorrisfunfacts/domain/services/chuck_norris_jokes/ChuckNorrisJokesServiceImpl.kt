package ru.heatalways.chucknorrisfunfacts.domain.services.chuck_norris_jokes

import retrofit2.Response
import ru.heatalways.chucknorrisfunfacts.data.entities.ChuckJoke
import ru.heatalways.chucknorrisfunfacts.domain.network.api.ChuckNorrisJokesApi
import ru.heatalways.chucknorrisfunfacts.domain.network.api_response.ApiResponse

class ChuckNorrisJokesServiceImpl(private val api: ChuckNorrisJokesApi): ChuckNorrisJokesService {
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
}