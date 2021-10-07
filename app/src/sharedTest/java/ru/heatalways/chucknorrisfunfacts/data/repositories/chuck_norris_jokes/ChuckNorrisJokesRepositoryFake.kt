package ru.heatalways.chucknorrisfunfacts.data.repositories.chuck_norris_jokes

import ru.heatalways.chucknorrisfunfacts.data.database.models.ChuckJokeEntity
import ru.heatalways.chucknorrisfunfacts.data.network.util.ResultNetwork
import ru.heatalways.chucknorrisfunfacts.domain.models.Category
import ru.heatalways.chucknorrisfunfacts.domain.models.ChuckJoke
import ru.heatalways.chucknorrisfunfacts.domain.repositories.chuck_norris_jokes.ChuckNorrisJokesRepository
import ru.heatalways.chucknorrisfunfacts.domain.utils.StringResource
import ru.heatalways.chucknorrisfunfacts.domain.utils.strRes
import ru.heatalways.chucknorrisfunfacts.mappers.toDomain
import ru.heatalways.chucknorrisfunfacts.mappers.toEntity
import java.util.*
import javax.inject.Inject

class ChuckNorrisJokesRepositoryFake @Inject constructor(
    // empty constructor
): ChuckNorrisJokesRepository {

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
    var trashJokes = mutableListOf<ChuckJokeEntity>()

    var shouldReturnErrorResponse = false

    private fun randomByCategory(category: String) =
        jokes
            .filter { it.categories.map {
                (it as Category.Specific).name
            }.contains(category) }
            .randomOrNull()


    override suspend fun random(category: String?): ResultNetwork<ChuckJoke> {
        return if (shouldReturnErrorResponse) {
            ResultNetwork.NetworkError
        } else {
            if (category == null) ResultNetwork.Success(jokes.random())
            else {
                val joke = randomByCategory(category)

                if (joke != null)
                    ResultNetwork.Success(joke)
                else
                    ResultNetwork.GenericError(
                        message = "No jokes for category \"blablabla\" found."
                    )
            }
        }
    }

    override suspend fun categories(): ResultNetwork<List<Category>> {
        return if (shouldReturnErrorResponse) {
            ResultNetwork.NetworkError
        } else {
            ResultNetwork.Success(categories)
        }
    }

    override suspend fun search(query: String): ResultNetwork<List<ChuckJoke>> {
        return if (shouldReturnErrorResponse) {
            ResultNetwork.NetworkError
        } else {
            ResultNetwork.Success(jokes.filter {
                (it.value as StringResource.ByString).text?.contains(query) ?: false
            })
        }
    }

    override suspend fun searchCategories(query: String): ResultNetwork<List<Category>> {
        return if (shouldReturnErrorResponse) ResultNetwork.NetworkError
        else ResultNetwork.Success(categories.filter { it.name.contains(query) })
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

    override suspend fun saveJokes(jokes: List<ChuckJoke>) {
        savedJokes.addAll(jokes.map { it.toEntity().copy(
            savedAt = Calendar.getInstance().timeInMillis
        ) })
    }

    override suspend fun removeAllSavedJokes(): Int {
        return savedJokes.size.also { savedJokes.clear() }
    }

    override suspend fun saveJokesToTrash() {
        trashJokes.clear()
        trashJokes.addAll(savedJokes)
        savedJokes.clear()
    }

    override suspend fun restoreJokesFromTrash(): List<ChuckJoke> {
        savedJokes.addAll(trashJokes)
        trashJokes.clear()
        return savedJokes.map { it.toDomain() }
    }
}