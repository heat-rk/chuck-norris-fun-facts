package ru.heatalways.chucknorrisfunfacts.random_joke.presentation.screen.random_joke

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import ru.heatalways.chucknorrisfunfacts.common.general.utils.strRes
import ru.heatalways.chucknorrisfunfacts.common.presentation.utils.ScrollState
import ru.heatalways.chucknorrisfunfacts.common.presentation.utils.ToastState
import ru.heatalways.chucknorrisfunfacts.common.utils.BaseViewModelTest
import ru.heatalways.chucknorrisfunfacts.core.domain.models.Category
import ru.heatalways.chucknorrisfunfacts.core_chuck_norris_jokes.data.repositories.chuck_norris_jokes.ChuckNorrisJokesRepositoryFake
import ru.heatalways.chucknorrisfunfacts.core_chuck_norris_jokes.domain.interactors.chuck_norris_jokes.ChuckNorrisJokesInteractor
import ru.heatalways.chucknorrisfunfacts.core_chuck_norris_jokes.domain.interactors.chuck_norris_jokes.ChuckNorrisJokesInteractorImpl
import ru.heatalways.chucknorrisfunfacts.core_chuck_norris_jokes.mappers.toEntity
import ru.heatalways.chucknorrisfunfacts.random_joke.R
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
class RandomJokeViewModelTest: BaseViewModelTest() {
    private lateinit var repository: ChuckNorrisJokesRepositoryFake
    private lateinit var interactor: ChuckNorrisJokesInteractor
    private lateinit var viewModel: RandomJokeViewModel

    @Before
    fun setup() {
        repository = ChuckNorrisJokesRepositoryFake()
        interactor = ChuckNorrisJokesInteractorImpl(repository)
        viewModel = RandomJokeViewModel(interactor)
    }

    @Test
    fun `test viewModel init, should return jokes`() = runTest {
        repository.savedJokes.add(repository.jokes.first().toEntity())

        viewModel.state.test {
            viewModel.fetchJokes()

            val loadingState = awaitItem()
            assertThat(loadingState.isLoading).isTrue()
            assertThat(loadingState.message).isNull()
            assertThat(loadingState.jokes).isEmpty()

            val loadedState = awaitItem()
            assertThat(loadedState.isLoading).isFalse()
            assertThat(loadedState.message).isNull()
            assertThat(loadedState.jokes).hasSize(1)

            assertThat(cancelAndConsumeRemainingEvents().size).isEqualTo(0)
        }
    }

    @Test
    fun `test viewModel init, should return empty list with message`() = runTest {
        viewModel.state.test {
            viewModel.fetchJokes()

            val loadingState = awaitItem()
            assertThat(loadingState.isLoading).isTrue()
            assertThat(loadingState.message).isNull()
            assertThat(loadingState.jokes).isEmpty()

            val loadedState = awaitItem()
            assertThat(loadedState.isLoading).isFalse()
            assertThat(loadedState.message).isEqualTo(strRes(R.string.random_joke_empty_history))
            assertThat(loadedState.jokes).isEmpty()

            assertThat(cancelAndConsumeRemainingEvents().size).isEqualTo(0)
        }
    }

    @Test
    fun `fetch joke from ANY category, returns list with new element`() = runTest {
        viewModel.state.test {
            awaitItem() // skip state before action

            viewModel.setAction(RandomJokeAction.RequestRandomJoke)

            val initialEmptyState = awaitItem()
            assertThat(initialEmptyState.jokes).isEmpty()
            assertThat(initialEmptyState.message).isNotNull()

            val jokeLoadingState = awaitItem()
            assertThat(jokeLoadingState.isJokeLoading).isTrue()

            val successState = awaitItem()
            assertThat(successState.isLoading).isFalse()
            assertThat(successState.jokes).hasSize(1)
            assertThat(successState.message).isNull()

            val scrollingUpState = awaitItem()
            assertThat(scrollingUpState.scrollState).isEqualTo(ScrollState.ScrollingUp)

            val scrollFinishedState = awaitItem()
            assertThat(scrollFinishedState.scrollState).isEqualTo(ScrollState.Stopped)

            assertThat(cancelAndConsumeRemainingEvents().size).isEqualTo(0)
        }
    }

    @Test
    fun `fetch joke from CAREER category, returns list with new element`() = runTest {
        viewModel.state.test {
            awaitItem() // skip state before action

            viewModel.selectCategory(Category.Specific("career"))
            viewModel.setAction(RandomJokeAction.RequestRandomJoke)

            val initialEmptyState = awaitItem()
            assertThat(initialEmptyState.category).isEqualTo(Category.Specific("career"))

            val categorySelectedState = awaitItem()
            assertThat(categorySelectedState.category).isEqualTo(
                Category.Specific("career")
            )

            val jokeLoadingState = awaitItem()
            assertThat(jokeLoadingState.isJokeLoading).isTrue()

            val successState = awaitItem()
            assertThat(successState.isLoading).isFalse()
            assertThat(successState.jokes).hasSize(1)
            assertThat(successState.jokes.first().categories)
                .contains(Category.Specific("career"))
            assertThat(successState.message).isNull()

            val scrollingUpState = awaitItem()
            assertThat(scrollingUpState.scrollState).isEqualTo(ScrollState.ScrollingUp)

            val scrollFinishedState = awaitItem()
            assertThat(scrollFinishedState.scrollState).isEqualTo(ScrollState.Stopped)

            assertThat(cancelAndConsumeRemainingEvents().size).isEqualTo(0)
        }
    }

    @Test
    fun `fetch joke from ANY category, returns error`() = runTest {
        repository.shouldReturnErrorResponse = true

        viewModel.state.test {
            awaitItem() // skip state before action

            viewModel.setAction(RandomJokeAction.RequestRandomJoke)

            val initialEmptyState = awaitItem()
            assertThat(initialEmptyState.jokes).isEmpty()
            assertThat(initialEmptyState.message).isNotNull()

            val jokeLoadingState = awaitItem()
            assertThat(jokeLoadingState.isJokeLoading).isTrue()

            val errorState = awaitItem()
            assertThat(errorState.isJokeLoading).isFalse()
            assertThat(errorState.jokes).hasSize(0)

            val toastShownState = awaitItem()
            assertThat(toastShownState.toastState).isEqualTo(
                ToastState.Shown(
                strRes(R.string.error_network)
            ))

            val toastHiddenState = awaitItem()
            assertThat(toastHiddenState.toastState).isEqualTo(ToastState.Hidden)

            assertThat(cancelAndConsumeRemainingEvents().size).isEqualTo(0)
        }
    }
}