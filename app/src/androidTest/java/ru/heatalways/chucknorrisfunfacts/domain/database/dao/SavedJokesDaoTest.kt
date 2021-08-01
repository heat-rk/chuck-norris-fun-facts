package ru.heatalways.chucknorrisfunfacts.domain.database.dao

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
import ru.heatalways.chucknorrisfunfacts.data.entities.ChuckJoke
import ru.heatalways.chucknorrisfunfacts.domain.database.AppDatabase
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
        dao.insert(ChuckJoke(
            categories = listOf("test", "fake"),
            createdAt = null,
            iconUrl = null,
            id = "2",
            updatedAt = null,
            url = null,
            value = "never gonna give you up"
        ))

        assertThat(dao.getAll()).hasSize(1)
    }
}