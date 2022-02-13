package ru.heatalways.chucknorrisfunfacts.core_chuck_norris_jokes.domain.repositories.chuck_norris_jokes

import ru.heatalways.chucknorrisfunfacts.core.domain.models.Category
import ru.heatalways.chucknorrisfunfacts.core.domain.models.ChuckJoke
import ru.heatalways.chucknorrisfunfacts.common.data.utils.ResultNetwork

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