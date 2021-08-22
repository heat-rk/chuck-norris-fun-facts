package ru.heatalways.chucknorrisfunfacts.data.database.saved_jokes

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import ru.heatalways.chucknorrisfunfacts.data.network.chuck_norris_jokes.ChuckJokeNetwork
import ru.heatalways.chucknorrisfunfacts.data.database.AppDatabase
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class SavedJokesDaoTest {
    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_app_db")
    lateinit var database: AppDatabase

    private lateinit var dao: SavedJokesDao

    @Before
    fun setup() {
        hiltRule.inject()
        dao = database.savedJokesDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun testJokeInsertion_shouldHasOneJokeInDatabase() = runBlockingTest {
        dao.insert(
            ChuckJokeNetwork(
            categories = listOf("test", "fake"),
            createdAt = null,
            iconUrl = null,
            id = "2",
            updatedAt = null,
            url = null,
            value = "never gonna give you up",
            savedAt = 0
        )
        )

        assertThat(dao.getAll()).hasSize(1)
    }

    @Test
    fun test3JokesInsertion_shouldHas3JokesInDatabase() = runBlockingTest {
        dao.insert(
            ChuckJokeNetwork(
            categories = listOf("test", "fake"),
            createdAt = null,
            iconUrl = null,
            id = "1",
            updatedAt = null,
            url = null,
            value = "never gonna give you up",
            savedAt = 0
        )
        )

        dao.insert(
            ChuckJokeNetwork(
            categories = emptyList(),
            createdAt = null,
            iconUrl = null,
            id = "2",
            updatedAt = null,
            url = null,
            value = "never gonna let you down",
            savedAt = 1
        )
        )

        dao.insert(
            ChuckJokeNetwork(
            categories = emptyList(),
            createdAt = null,
            iconUrl = null,
            id = "3",
            updatedAt = null,
            url = null,
            value = "never gonna run around",
            savedAt = 2
        )
        )

        assertThat(dao.getAll()).hasSize(3)
    }

    @Test
    fun testLimit_shouldReturn2Jokes() = runBlockingTest {
        dao.insert(
            ChuckJokeNetwork(
            categories = listOf("test", "fake"),
            createdAt = null,
            iconUrl = null,
            id = "1",
            updatedAt = null,
            url = null,
            value = "never gonna give you up",
            savedAt = 0
        )
        )

        dao.insert(
            ChuckJokeNetwork(
            categories = emptyList(),
            createdAt = null,
            iconUrl = null,
            id = "2",
            updatedAt = null,
            url = null,
            value = "never gonna let you down",
            savedAt = 1
        )
        )

        dao.insert(
            ChuckJokeNetwork(
            categories = emptyList(),
            createdAt = null,
            iconUrl = null,
            id = "3",
            updatedAt = null,
            url = null,
            value = "never gonna run around",
            savedAt = 2
        )
        )

        assertThat(dao.getBy(limit = 2)).hasSize(2)
    }

    @Test
    fun testOffset_shouldReturn2JokesStartingWithId2() = runBlockingTest {
        dao.insert(
            ChuckJokeNetwork(
            categories = listOf("test", "fake"),
            createdAt = null,
            iconUrl = null,
            id = "1",
            updatedAt = null,
            url = null,
            value = "never gonna give you up",
            savedAt = 0
        )
        )

        dao.insert(
            ChuckJokeNetwork(
            categories = emptyList(),
            createdAt = null,
            iconUrl = null,
            id = "2",
            updatedAt = null,
            url = null,
            value = "never gonna let you down",
            savedAt = 1
        )
        )

        dao.insert(
            ChuckJokeNetwork(
            categories = emptyList(),
            createdAt = null,
            iconUrl = null,
            id = "3",
            updatedAt = null,
            url = null,
            value = "never gonna run around",
            savedAt = 2
        )
        )

        val result = dao.getBy(offset = 1)

        assertThat(result).hasSize(2)
        assertThat(result[0].id).isEqualTo("2")
        assertThat(result[1].id).isEqualTo("1")
    }

    @Test
    fun testOffsetAndLimit_shouldReturnJokeWithId2() = runBlockingTest {
        dao.insert(
            ChuckJokeNetwork(
            categories = listOf("test", "fake"),
            createdAt = null,
            iconUrl = null,
            id = "1",
            updatedAt = null,
            url = null,
            value = "never gonna give you up",
            savedAt = 0
        )
        )

        dao.insert(
            ChuckJokeNetwork(
            categories = emptyList(),
            createdAt = null,
            iconUrl = null,
            id = "2",
            updatedAt = null,
            url = null,
            value = "never gonna let you down",
            savedAt = 1
        )
        )

        dao.insert(
            ChuckJokeNetwork(
            categories = emptyList(),
            createdAt = null,
            iconUrl = null,
            id = "3",
            updatedAt = null,
            url = null,
            value = "never gonna run around",
            savedAt = 2
        )
        )

        val result = dao.getBy(offset = 1, limit = 1)

        assertThat(result).hasSize(1)
        assertThat(result.first().id).isEqualTo("2")
    }

    @Test
    fun testDelete_shouldReturnEmptyList() = runBlockingTest {
        val item = ChuckJokeNetwork(
            categories = listOf("test", "fake"),
            createdAt = null,
            iconUrl = null,
            id = "1",
            updatedAt = null,
            url = null,
            value = "never gonna give you up",
            savedAt = 0
        )

        dao.insert(item)
        dao.delete(item)

        assertThat(dao.getAll()).isEmpty()
    }
}