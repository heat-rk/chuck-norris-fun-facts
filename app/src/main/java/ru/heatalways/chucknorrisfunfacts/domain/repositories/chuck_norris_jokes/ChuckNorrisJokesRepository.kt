package ru.heatalways.chucknorrisfunfacts.domain.repositories.chuck_norris_jokes

import ru.heatalways.chucknorrisfunfacts.data.network.util.ResultNetwork
import ru.heatalways.chucknorrisfunfacts.domain.models.Category
import ru.heatalways.chucknorrisfunfacts.domain.models.ChuckJoke

interface ChuckNorrisJokesRepository {
    suspend fun random(category: String? = null): ResultNetwork<ChuckJoke>
    suspend fun categories(): ResultNetwork<List<Category>>
    suspend fun searchCategories(query: String): ResultNetwork<List<Category>>
    suspend fun search(query: String): ResultNetwork<List<ChuckJoke>>

    suspend fun saveJoke(joke: ChuckJoke)
    suspend fun saveJokes(jokes: List<ChuckJoke>)
    suspend fun getSavedJokesBy(limit: Int = 10, offset: Int = 0): List<ChuckJoke>
    suspend fun getAllSavedJokes(): List<ChuckJoke>
    suspend fun removeAllSavedJokes(): Int
    suspend fun saveJokesToTrash()
    suspend fun restoreJokesFromTrash(): List<ChuckJoke>
}