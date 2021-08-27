package ru.heatalways.chucknorrisfunfacts.domain.repositories.chuck_norris_jokes

import ru.heatalways.chucknorrisfunfacts.data.database.saved_jokes.ChuckJokeEntity
import ru.heatalways.chucknorrisfunfacts.data.network.util.ResultWrapper
import ru.heatalways.chucknorrisfunfacts.domain.utils.StringResource
import ru.heatalways.chucknorrisfunfacts.domain.utils.strRes
import ru.heatalways.chucknorrisfunfacts.mappers.toDomain
import ru.heatalways.chucknorrisfunfacts.mappers.toEntity
import java.util.*

class ChuckNorrisJokesRepositoryFake: ChuckNorrisJokesRepository {
    private val categories = listOf(
        Category.Specific("animal"), Category.Specific("career")
    )

    val jokes = listOf(
        ChuckJoke(
            categories = emptyList(),
            createdAt = null,
            iconUrl = null,
            id = "1",
            updatedAt = null,
            url = null,
            value = strRes("that joke said hey"),
            savedAt = null
        ),
        ChuckJoke(
            categories = listOf(categories[0], categories[1]),
            createdAt = null,
            iconUrl = null,
            id = "2",
            updatedAt = null,
            url = null,
            value = strRes("never gonna give you up"),
            savedAt = null
        ),
        ChuckJoke(
            categories = listOf(categories[0]),
            createdAt = null,
            iconUrl = null,
            id = "3",
            updatedAt = null,
            url = null,
            value = strRes("wrong side of heaven"),
            savedAt = null
        )
    )

    var savedJokes = mutableListOf<ChuckJokeEntity>()

    var shouldReturnErrorResponse = false

    private fun randomByCategory(category: String) =
        jokes
            .filter { it.categories.map {
                (it as Category.Specific).name
            }.contains(category) }
            .randomOrNull()


    override suspend fun random(category: String?): ResultWrapper<ChuckJoke> {
        return if (shouldReturnErrorResponse) {
            ResultWrapper.NetworkError
        } else {
            if (category == null) ResultWrapper.Success(jokes.random())
            else {
                val joke = randomByCategory(category)

                if (joke != null)
                    ResultWrapper.Success(joke)
                else
                    ResultWrapper.GenericError(
                        message = "No jokes for category \"blablabla\" found."
                    )
            }
        }
    }

    override suspend fun categories(): ResultWrapper<List<Category>> {
        return if (shouldReturnErrorResponse) {
            ResultWrapper.NetworkError
        } else {
            ResultWrapper.Success(categories)
        }
    }

    override suspend fun search(query: String): ResultWrapper<List<ChuckJoke>> {
        return if (shouldReturnErrorResponse) {
            ResultWrapper.NetworkError
        } else {
            ResultWrapper.Success(jokes.filter {
                (it.value as StringResource.ByString).text?.contains(query) ?: false
            })
        }
    }

    override suspend fun saveJoke(joke: ChuckJoke) {
        savedJokes.add(joke.toEntity().copy(
            savedAt = Calendar.getInstance().timeInMillis
        ))
    }

    override suspend fun getSavedJokesBy(limit: Int, offset: Int): List<ChuckJoke> {
        val from = offset
        val to = from + limit

        if (from > savedJokes.lastIndex)
            return emptyList()

        return savedJokes.subList(
            fromIndex = from,
            toIndex = minOf(to, savedJokes.size)
        ).sortedByDescending { it.savedAt }.map { it.toDomain() }
    }

    override suspend fun getAllSavedJokes(): List<ChuckJoke> {
        return savedJokes.sortedByDescending { it.savedAt }.map { it.toDomain() }
    }
}