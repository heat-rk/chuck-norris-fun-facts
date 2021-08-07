package ru.heatalways.chucknorrisfunfacts.presentation.screen.random_joke

import app.cash.turbine.test
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import ru.heatalways.chucknorrisfunfacts.data.entities.Category
import ru.heatalways.chucknorrisfunfacts.domain.managers.ChuckNorrisJokesManagerFake
import ru.heatalways.chucknorrisfunfacts.utils.BaseViewModelTest
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
class RandomJokeViewModelTest: BaseViewModelTest() {
    private lateinit var fakeManager:  ChuckNorrisJokesManagerFake
    private lateinit var viewModel: RandomJokeViewModel

    @Before
    fun setup() {
        fakeManager = ChuckNorrisJokesManagerFake()
        viewModel = RandomJokeViewModel(fakeManager)
    }

    @Test
    fun `fetch joke from ANY category, returns list with new element`() = coroutineRule.runBlockingTest {
        viewModel.state.test {
            viewModel.setAction(RandomJokeContract.Action.OnRandomJokeRequest(Category.Any))

            val value = expectMostRecentItem()

            Truth.assertThat(value).isInstanceOf(RandomJokeContract.State.Loaded::class.java)
            if (value is RandomJokeContract.State.Loaded) {
                Truth.assertThat(value.jokes).hasSize(1)
            }
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `fetch joke from CAREER category, returns list with new element`() = coroutineRule.runBlockingTest {
        viewModel.state.test {
            viewModel.setAction(RandomJokeContract.Action.OnRandomJokeRequest(
                Category.Specific("career")
            ))

            val value = expectMostRecentItem()

            Truth.assertThat(value).isInstanceOf(RandomJokeContract.State.Loaded::class.java)
            if (value is RandomJokeContract.State.Loaded) {
                Truth.assertThat(value.jokes).hasSize(1)
                Truth.assertThat(value.jokes.first().categories).contains("career")
            }
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `fetch joke from ANY category, returns error`() = coroutineRule.runBlockingTest {
        fakeManager.shouldReturnErrorResponse = true
        viewModel.effect.test {
            viewModel.setAction(RandomJokeContract.Action.OnRandomJokeRequest(Category.Any))

            val value = expectMostRecentItem()

            Truth.assertThat(value).isInstanceOf(RandomJokeContract.Effect.Error::class.java)
            cancelAndIgnoreRemainingEvents()
        }
    }
}