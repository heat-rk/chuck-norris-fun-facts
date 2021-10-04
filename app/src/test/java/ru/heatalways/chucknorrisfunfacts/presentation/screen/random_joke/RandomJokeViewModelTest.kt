package ru.heatalways.chucknorrisfunfacts.presentation.screen.random_joke

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.github.terrakok.cicerone.Router
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import ru.heatalways.chucknorrisfunfacts.R
import ru.heatalways.chucknorrisfunfacts.domain.interactors.chuck_norris_jokes.ChuckNorrisJokesInteractor
import ru.heatalways.chucknorrisfunfacts.domain.interactors.chuck_norris_jokes.ChuckNorrisJokesInteractorImpl
import ru.heatalways.chucknorrisfunfacts.domain.repositories.chuck_norris_jokes.Category
import ru.heatalways.chucknorrisfunfacts.domain.repositories.chuck_norris_jokes.ChuckNorrisJokesRepositoryFake
import ru.heatalways.chucknorrisfunfacts.presentation.util.ToastState
import ru.heatalways.chucknorrisfunfacts.domain.utils.strRes
import ru.heatalways.chucknorrisfunfacts.mappers.toEntity
import ru.heatalways.chucknorrisfunfacts.utils.BaseViewModelTest
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
        viewModel = RandomJokeViewModel(
            interactor,
            SavedStateHandle(),
            Router()
        )
        viewModel.resetState()
    }

    @Test
    fun `test viewModel init, should return jokes`() = coroutineRule.runBlockingTest {
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
    fun `test viewModel init, should return empty list with message`() = coroutineRule.runBlockingTest {
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
    fun `fetch joke from ANY category, returns list with new element`() =
        coroutineRule.runBlockingTest {
            viewModel.fetchJokes()

            viewModel.state.test {
                viewModel.setAction(RandomJokeAction.RequestRandomJoke)

                awaitItem() // skip state before action

                val jokeLoadingState = awaitItem()
                assertThat(jokeLoadingState.isJokeLoading).isTrue()

                val successState = awaitItem()
                assertThat(successState.isLoading).isFalse()
                assertThat(successState.jokes).hasSize(1)
                assertThat(successState.message).isNull()

                val scrollingUpState = awaitItem()
                assertThat(scrollingUpState.isScrollingUp).isTrue()

                val scrollFinishedState = awaitItem()
                assertThat(scrollFinishedState.isScrollingUp).isFalse()

                assertThat(cancelAndConsumeRemainingEvents().size).isEqualTo(0)
            }
        }

    @Test
    fun `fetch joke from CAREER category, returns list with new element`() =
        coroutineRule.runBlockingTest {
            viewModel.fetchJokes()

            viewModel.state.test {
                viewModel.selectCategory(Category.Specific("career"))
                viewModel.setAction(RandomJokeAction.RequestRandomJoke)

                awaitItem() // skip state before action

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
                assertThat(scrollingUpState.isScrollingUp).isTrue()

                val scrollFinishedState = awaitItem()
                assertThat(scrollFinishedState.isScrollingUp).isFalse()

                assertThat(cancelAndConsumeRemainingEvents().size).isEqualTo(0)
            }
        }

    @Test
    fun `fetch joke from ANY category, returns error`() = coroutineRule.runBlockingTest {
        repository.shouldReturnErrorResponse = true
        viewModel.fetchJokes()

        viewModel.state.test {
            viewModel.setAction(RandomJokeAction.RequestRandomJoke)

            awaitItem() // skip state before action

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