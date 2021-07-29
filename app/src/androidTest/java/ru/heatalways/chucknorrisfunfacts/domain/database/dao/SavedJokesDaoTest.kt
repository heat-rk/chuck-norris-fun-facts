package ru.heatalways.chucknorrisfunfacts.domain.database.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import ru.heatalways.chucknorrisfunfacts.domain.database.AppDatabase
import javax.inject.Inject
import javax.inject.Named

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
    fun test() {

    }
}