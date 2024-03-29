package ru.heatalways.chucknorrisfunfacts.core_chuck_norris_jokes.data.network.chuck_norris_jokes

import retrofit2.http.GET
import retrofit2.http.Query
import ru.heatalways.chucknorrisfunfacts.core_chuck_norris_jokes.data.network.models.ChuckJokeNetwork
import ru.heatalways.chucknorrisfunfacts.core_chuck_norris_jokes.data.network.models.SearchResponse

internal interface ChuckNorrisJokesApi {
    @GET("random")
    suspend fun random(
        @Query("category") category: String? = null
    ): ChuckJokeNetwork

    @GET("categories")
    suspend fun categories(): List<String>

    @GET("search")
    suspend fun search(
        @Query("query") query: String
    ): SearchResponse

    companion object {
        const val BASE_URL = "https://api.chucknorris.io/jokes/"
    }
}