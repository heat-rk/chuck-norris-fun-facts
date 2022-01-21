package ru.heatalways.chucknorrisfunfacts.presentation.screen.search_joke

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import ru.heatalways.chucknorrisfunfacts.R
import ru.heatalways.chucknorrisfunfacts.core.utils.strRes
import ru.heatalways.chucknorrisfunfacts.data.repositories.chuck_norris_jokes.ChuckNorrisJokesRepositoryFake
import ru.heatalways.chucknorrisfunfacts.domain.interactors.chuck_norris_jokes.ChuckNorrisJokesInteractor
import ru.heatalways.chucknorrisfunfacts.domain.interactors.chuck_norris_jokes.ChuckNorrisJokesInteractorImpl
import ru.heatalways.chucknorrisfunfacts.presentation.util.ScrollState
import ru.heatalways.chucknorrisfunfacts.utils.BaseViewModelTest
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
class SearchJokeViewModelTest: BaseViewModelTest() {
    private lateinit var repository: ChuckNorrisJokesRepositoryFake
    private lateinit var interactor: ChuckNorrisJokesInteractor
    private lateinit var viewModel: SearchJokeViewModel

    @Before
    fun setup() {
        repository = ChuckNorrisJokesRepositoryFake()
        interactor = ChuckNorrisJokesInteractorImpl(repository)
        viewModel = SearchJokeViewModel(interactor, SavedStateHandle())
    }

    @Test
    fun `valid search query, returns 1 element`() = runTest {
        viewModel.state.test {
            viewModel.setAction(SearchJokeAction.OnSearchExecute("hey"))

            awaitItem() // skip state before action

            val loadingState = awaitItem()
            assertThat(loadingState.isJokesLoading).isTrue()

            val successState = awaitItem()
            assertThat(successState.isJokesLoading).isFalse()
            assertThat(successState.jokes).hasSize(1)
            assertThat(successState.jokesMessage).isNull()

            val scrollingUpState = awaitItem()
            assertThat(scrollingUpState.scrollState).isEqualTo(ScrollState.ScrollingUp)

            val scrollFinishedState = awaitItem()
            assertThat(scrollFinishedState.scrollState).isEqualTo(ScrollState.Stopped)

            assertThat(cancelAndConsumeRemainingEvents().size).isEqualTo(0)
        }
    }

    @Test
    fun `invalid search query, returns empty list`() = runTest {
        viewModel.state.test {
            viewModel.setAction(SearchJokeAction.OnSearchExecute("heyvsdvdbjhdfjdf"))

            awaitItem() // skip state before action

            val loadingState = awaitItem()
            assertThat(loadingState.isJokesLoading).isTrue()

            val errorState = awaitItem()
            assertThat(errorState.isJokesLoading).isFalse()
            assertThat(errorState.jokes).isEmpty()
            assertThat(errorState.jokesMessage).isNotNull()
            assertThat(errorState.jokesMessage).isEqualTo(strRes(R.string.error_not_found))

            assertThat(cancelAndConsumeRemainingEvents().size).isEqualTo(0)
        }
    }

    @Test
    fun `valid search query, returns error`() = runTest {
        repository.shouldReturnErrorResponse = true
        viewModel.state.test {
            viewModel.setAction(SearchJokeAction.OnSearchExecute("hey"))

            awaitItem() // skip state before action

            val loadingState = awaitItem()
            assertThat(loadingState.isJokesLoading).isTrue()

            val errorState = awaitItem()
            assertThat(errorState.isJokesLoading).isFalse()
            assertThat(errorState.jokes).isEmpty()
            assertThat(errorState.jokesMessage).isNotNull()
            assertThat(errorState.jokesMessage).isEqualTo(strRes(R.string.error_network))

            assertThat(cancelAndConsumeRemainingEvents().size).isEqualTo(0)
        }
    }
}