package ru.heatalways.chucknorrisfunfacts.presentation.screen.search_joke

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import ru.heatalways.chucknorrisfunfacts.R
import ru.heatalways.chucknorrisfunfacts.domain.interactors.search_joke.SearchJokeAction
import ru.heatalways.chucknorrisfunfacts.domain.interactors.search_joke.SearchJokeInteractor
import ru.heatalways.chucknorrisfunfacts.domain.interactors.search_joke.SearchJokeInteractorImpl
import ru.heatalways.chucknorrisfunfacts.domain.repositories.chuck_norris_jokes.ChuckNorrisJokesRepositoryFake
import ru.heatalways.chucknorrisfunfacts.domain.utils.strRes
import ru.heatalways.chucknorrisfunfacts.utils.BaseViewModelTest
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
class SearchJokeViewModelTest: BaseViewModelTest() {
    private lateinit var repository: ChuckNorrisJokesRepositoryFake
    private lateinit var interactor: SearchJokeInteractor
    private lateinit var viewModel: SearchJokeViewModel

    @Before
    fun setup() {
        repository = ChuckNorrisJokesRepositoryFake()
        interactor = SearchJokeInteractorImpl(repository)
        viewModel = SearchJokeViewModel(interactor, SavedStateHandle())
    }

    @Test
    fun `valid search query, returns 1 element`() = coroutineRule.runBlockingTest {
        viewModel.state.test {
            viewModel.setAction(SearchJokeAction.OnSearchExecute("hey"))

            awaitItem() // skip state before action

            val loadingState = awaitItem()
            assertThat(loadingState.isLoading).isTrue()

            val successState = awaitItem()
            assertThat(successState.isLoading).isFalse()
            assertThat(successState.jokes).hasSize(1)
            assertThat(successState.message).isNull()

            assertThat(cancelAndConsumeRemainingEvents().size).isEqualTo(0)
        }
    }

    @Test
    fun `invalid search query, returns empty list`() = coroutineRule.runBlockingTest {
        viewModel.state.test {
            viewModel.setAction(SearchJokeAction.OnSearchExecute("heyvsdvdbjhdfjdf"))

            awaitItem() // skip state before action

            val loadingState = awaitItem()
            assertThat(loadingState.isLoading).isTrue()

            val errorState = awaitItem()
            assertThat(errorState.isLoading).isFalse()
            assertThat(errorState.jokes).isEmpty()
            assertThat(errorState.message).isNotNull()
            assertThat(errorState.message).isEqualTo(strRes(R.string.error_not_found))

            assertThat(cancelAndConsumeRemainingEvents().size).isEqualTo(0)
        }
    }

    @Test
    fun `valid search query, returns error`() = coroutineRule.runBlockingTest {
        repository.shouldReturnErrorResponse = true
        viewModel.state.test {
            viewModel.setAction(SearchJokeAction.OnSearchExecute("hey"))

            awaitItem() // skip state before action

            val loadingState = awaitItem()
            assertThat(loadingState.isLoading).isTrue()

            val errorState = awaitItem()
            assertThat(errorState.isLoading).isFalse()
            assertThat(errorState.jokes).isEmpty()
            assertThat(errorState.message).isNotNull()
            assertThat(errorState.message).isEqualTo(strRes(R.string.error_network))

            assertThat(cancelAndConsumeRemainingEvents().size).isEqualTo(0)
        }
    }
}