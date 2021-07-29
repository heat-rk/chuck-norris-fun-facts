package ru.heatalways.chucknorrisfunfacts.presentation.search_joke

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import ru.heatalways.chucknorrisfunfacts.domain.managers.ChuckNorrisJokesManagerFake
import ru.heatalways.chucknorrisfunfacts.presentation.screen.search_joke.SearchJokeState
import ru.heatalways.chucknorrisfunfacts.presentation.screen.search_joke.SearchJokeViewModel
import ru.heatalways.chucknorrisfunfacts.utils.BaseViewModelTest
import ru.heatalways.chucknorrisfunfacts.utils.getOrAwaitValueTest

@ExperimentalCoroutinesApi
class SearchJokeViewModelTest: BaseViewModelTest() {
    private val fakeManager =  ChuckNorrisJokesManagerFake()
    private lateinit var viewModel: SearchJokeViewModel

    @Before
    fun setup() {
        viewModel = SearchJokeViewModel(fakeManager)
    }

    @Test
    fun `valid search query, returns 1 element`() = coroutineTest {
        viewModel.onSearchQueryExecute("hey")

        val value = viewModel.state.getOrAwaitValueTest()

        assertThat(value).isInstanceOf(SearchJokeState.Loaded::class.java)
        if (value is SearchJokeState.Loaded) {
            assertThat(value.jokes).hasSize(1)
        }
    }

    @Test
    fun `invalid search query, returns empty list`() = coroutineTest {
        viewModel.onSearchQueryExecute("heyvsdvdbjhdfjdf")

        val value = viewModel.state.getOrAwaitValueTest()

        assertThat(value).isInstanceOf(SearchJokeState.Empty::class.java)
    }

    @Test
    fun `valid search query, returns error`() = coroutineTest {
        fakeManager.shouldReturnErrorResponse = true
        viewModel.onSearchQueryExecute("hey")

        val value = viewModel.state.getOrAwaitValueTest()

        assertThat(value).isInstanceOf(SearchJokeState.Error::class.java)
    }
}