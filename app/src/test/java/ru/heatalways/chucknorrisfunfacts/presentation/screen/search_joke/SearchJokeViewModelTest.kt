package ru.heatalways.chucknorrisfunfacts.presentation.screen.search_joke

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import ru.heatalways.chucknorrisfunfacts.domain.managers.ChuckNorrisJokesManagerFake
import ru.heatalways.chucknorrisfunfacts.utils.BaseViewModelTest
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
class SearchJokeViewModelTest: BaseViewModelTest() {
    private lateinit var fakeManager: ChuckNorrisJokesManagerFake
    private lateinit var viewModel: SearchJokeViewModel

    @Before
    fun setup() {
        fakeManager = ChuckNorrisJokesManagerFake()
        viewModel = SearchJokeViewModel(fakeManager)
    }

    @Test
    fun `valid search query, returns 1 element`() = coroutineRule.runBlockingTest {
        viewModel.state.test {
            viewModel.setAction(SearchJokeContract.Action.OnSearchExecute("hey"))

            val value = expectMostRecentItem()

            assertThat(value).isInstanceOf(SearchJokeContract.State.Loaded::class.java)
            if (value is SearchJokeContract.State.Loaded) {
                assertThat(value.jokes).hasSize(1)
            }
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `invalid search query, returns empty list`() = coroutineRule.runBlockingTest {
        viewModel.state.test {
            viewModel.setAction(SearchJokeContract.Action.OnSearchExecute("heyvsdvdbjhdfjdf"))

            val value = expectMostRecentItem()

            assertThat(value).isInstanceOf(SearchJokeContract.State.Empty::class.java)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `valid search query, returns error`() = coroutineRule.runBlockingTest {
        fakeManager.shouldReturnErrorResponse = true
        viewModel.state.test {
            viewModel.setAction(SearchJokeContract.Action.OnSearchExecute("hey"))

            val value = expectMostRecentItem()

            assertThat(value).isInstanceOf(SearchJokeContract.State.Error::class.java)
            cancelAndIgnoreRemainingEvents()
        }
    }
}