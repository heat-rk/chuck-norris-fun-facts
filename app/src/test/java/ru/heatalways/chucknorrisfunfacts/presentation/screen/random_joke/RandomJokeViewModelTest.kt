package ru.heatalways.chucknorrisfunfacts.presentation.screen.random_joke

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import ru.heatalways.chucknorrisfunfacts.R
import ru.heatalways.chucknorrisfunfacts.domain.interactors.random_joke.RandomJokeAction
import ru.heatalways.chucknorrisfunfacts.domain.interactors.random_joke.RandomJokeInteractor
import ru.heatalways.chucknorrisfunfacts.domain.interactors.random_joke.RandomJokeInteractorImpl
import ru.heatalways.chucknorrisfunfacts.domain.interactors.random_joke.RandomJokeViewEffect
import ru.heatalways.chucknorrisfunfacts.domain.interactors.random_joke.select_category.CategorySelectionInteractor
import ru.heatalways.chucknorrisfunfacts.domain.interactors.random_joke.select_category.CategorySelectionInteractorImpl
import ru.heatalways.chucknorrisfunfacts.domain.repositories.chuck_norris_jokes.Category
import ru.heatalways.chucknorrisfunfacts.domain.repositories.chuck_norris_jokes.ChuckNorrisJokesRepositoryFake
import ru.heatalways.chucknorrisfunfacts.domain.utils.strRes
import ru.heatalways.chucknorrisfunfacts.mappers.toEntity
import ru.heatalways.chucknorrisfunfacts.utils.BaseViewModelTest
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
class RandomJokeViewModelTest: BaseViewModelTest() {
    private lateinit var repository: ChuckNorrisJokesRepositoryFake
    private lateinit var randomJokeInteractor: RandomJokeInteractor
    private lateinit var categorySelectionInteractor: CategorySelectionInteractor
    private lateinit var viewModel: RandomJokeViewModel

    @Before
    fun setup() {
        repository = ChuckNorrisJokesRepositoryFake()
        randomJokeInteractor = RandomJokeInteractorImpl(repository)
        categorySelectionInteractor = CategorySelectionInteractorImpl(repository)
        viewModel = RandomJokeViewModel(
            randomJokeInteractor,
            categorySelectionInteractor,
            SavedStateHandle()
        )
        viewModel.resetState()
    }

    @Test
    fun `test viewModel init, should return jokes`() = coroutineRule.runBlockingTest {
        repository.savedJokes.add(repository.jokes.first().toEntity())

        viewModel.state.test {
            viewModel.collectSelectedCategory()
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
            viewModel.collectSelectedCategory()
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
            viewModel.collectSelectedCategory()
            viewModel.fetchJokes()

            viewModel.state.test {
                viewModel.setAction(RandomJokeAction.OnRandomJokeRequest)

                awaitItem() // skip state before action

                val jokeLoadingState = awaitItem()
                assertThat(jokeLoadingState.isJokeLoading).isTrue()

                val successState = awaitItem()
                assertThat(successState.isLoading).isFalse()
                assertThat(successState.jokes).hasSize(1)
                assertThat(successState.message).isNull()

                assertThat(cancelAndConsumeRemainingEvents().size).isEqualTo(0)
            }
        }

    @Test
    fun `fetch joke from CAREER category, returns list with new element`() =
        coroutineRule.runBlockingTest {
            viewModel.collectSelectedCategory()
            viewModel.fetchJokes()

            viewModel.state.test {
                categorySelectionInteractor.selectCategory(Category.Specific("career"))
                viewModel.setAction(RandomJokeAction.OnRandomJokeRequest)

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

                assertThat(cancelAndConsumeRemainingEvents().size).isEqualTo(0)
            }
        }

    @Test
    fun `fetch joke from ANY category, returns error`() = coroutineRule.runBlockingTest {
        repository.shouldReturnErrorResponse = true
        viewModel.collectSelectedCategory()
        viewModel.fetchJokes()

        viewModel.state.test {
            viewModel.setAction(RandomJokeAction.OnRandomJokeRequest)

            awaitItem() // skip state before action

            val jokeLoadingState = awaitItem()
            assertThat(jokeLoadingState.isJokeLoading).isTrue()

            val errorState = awaitItem()
            assertThat(errorState.isJokeLoading).isFalse()
            assertThat(errorState.jokes).hasSize(0)
            viewModel.effect.test {
                val effectState = awaitItem()
                assertThat(effectState).isEqualTo(RandomJokeViewEffect.Error(
                    strRes(R.string.error_network)
                ))
            }

            assertThat(cancelAndConsumeRemainingEvents().size).isEqualTo(0)
        }
    }
}