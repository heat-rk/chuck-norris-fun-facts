package ru.heatalways.chucknorrisfunfacts.data.database.saved_jokes

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import ru.heatalways.chucknorrisfunfacts.appComponent
import ru.heatalways.chucknorrisfunfacts.data.database.AppDatabase
import ru.heatalways.chucknorrisfunfacts.data.database.models.ChuckJokeEntity
import ru.heatalways.chucknorrisfunfacts.di.TestAppComponent

@ExperimentalCoroutinesApi
@SmallTest
class SavedJokesDaoTest {
    @get:Rule(order = 1)
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AppDatabase
    private lateinit var dao: SavedJokesDao

    @Before
    fun setup() {
        database = (InstrumentationRegistry.getInstrumentation().targetContext.appComponent as TestAppComponent)
            .database

        dao = database.savedJokesDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun testJokeInsertion_shouldHasOneJokeInDatabase() = runBlockingTest {
        dao.insert(entities.first())

        assertThat(dao.getAll()).hasSize(1)
    }

    @Test
    fun test3JokesInsertion_shouldHas3JokesInDatabase() = runBlockingTest {
        dao.insert(entities[0])
        dao.insert(entities[1])
        dao.insert(entities[2])

        assertThat(dao.getAll()).hasSize(3)
    }

    @Test
    fun testLimit_shouldReturn2Jokes() = runBlockingTest {
        dao.insert(entities[0])
        dao.insert(entities[1])
        dao.insert(entities[2])

        assertThat(dao.getBy(limit = 2)).hasSize(2)
    }

    @Test
    fun testOffset_shouldReturn2JokesStartingWithId2() = runBlockingTest {
        dao.insert(entities[0])
        dao.insert(entities[1])
        dao.insert(entities[2])

        val result = dao.getBy(offset = 1)

        assertThat(result).hasSize(2)
        assertThat(result[0].id).isEqualTo("2")
        assertThat(result[1].id).isEqualTo("1")
    }

    @Test
    fun testOffsetAndLimit_shouldReturnJokeWithId2() = runBlockingTest {
        dao.insert(entities[0])
        dao.insert(entities[1])
        dao.insert(entities[2])

        val result = dao.getBy(offset = 1, limit = 1)

        assertThat(result).hasSize(1)
        assertThat(result.first().id).isEqualTo("2")
    }

    @Test
    fun testDelete_shouldReturnEmptyList() = runBlockingTest {
        val item = entities[0]

        dao.insert(item)
        dao.delete(item)

        assertThat(dao.getAll()).isEmpty()
    }


    companion object {
        private val entities = listOf(
            ChuckJokeEntity(
                categories = listOf("test", "fake"),
                createdAt = null,
                iconUrl = null,
                id = "1",
                actualId = 1,
                updatedAt = null,
                url = null,
                value = "never gonna give you up",
                savedAt = 0
            ),
            ChuckJokeEntity(
                categories = emptyList(),
                createdAt = null,
                iconUrl = null,
                id = "2",
                actualId = 2,
                updatedAt = null,
                url = null,
                value = "never gonna let you down",
                savedAt = 1
            ),
            ChuckJokeEntity(
                categories = emptyList(),
                createdAt = null,
                iconUrl = null,
                id = "3",
                actualId = 3,
                updatedAt = null,
                url = null,
                value = "never gonna run around",
                savedAt = 2
            )
        )
    }
}