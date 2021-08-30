package ru.heatalways.chucknorrisfunfacts.presentation.screen.random_joke.select_category

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import ru.heatalways.chucknorrisfunfacts.R
import ru.heatalways.chucknorrisfunfacts.domain.interactors.random_joke.select_category.CategorySelectionAction
import ru.heatalways.chucknorrisfunfacts.domain.interactors.random_joke.select_category.CategorySelectionEffect
import ru.heatalways.chucknorrisfunfacts.domain.interactors.random_joke.select_category.CategorySelectionInteractor
import ru.heatalways.chucknorrisfunfacts.domain.interactors.random_joke.select_category.CategorySelectionInteractorImpl
import ru.heatalways.chucknorrisfunfacts.domain.repositories.chuck_norris_jokes.Category
import ru.heatalways.chucknorrisfunfacts.domain.repositories.chuck_norris_jokes.ChuckNorrisJokesRepositoryFake
import ru.heatalways.chucknorrisfunfacts.domain.utils.strRes
import ru.heatalways.chucknorrisfunfacts.utils.BaseViewModelTest
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
class CategorySelectionViewModelTest: BaseViewModelTest() {
    private lateinit var repositoryFake: ChuckNorrisJokesRepositoryFake
    private lateinit var interactor: CategorySelectionInteractor
    private lateinit var viewModel: CategorySelectionViewModel

    @Before
    fun setup() {
        repositoryFake = ChuckNorrisJokesRepositoryFake()
        interactor = CategorySelectionInteractorImpl(repositoryFake)
        viewModel = CategorySelectionViewModel(interactor, SavedStateHandle())
        viewModel.resetState()
    }

    @Test
    fun `test viewModel init, returns 3 categories`() = coroutineRule.runBlockingTest {
        viewModel.state.test {
            viewModel.fetchCategories()

            val loadingState = awaitItem()
            assertThat(loadingState.isLoading).isTrue()
            assertThat(loadingState.categories).isEmpty()

            val loadedState = awaitItem()
            assertThat(loadedState.isLoading).isFalse()
            assertThat(loadedState.categories).hasSize(3)
        }
    }

    @Test
    fun `test viewModel init, returns error`() = coroutineRule.runBlockingTest {
        viewModel.state.test {
            repositoryFake.shouldReturnErrorResponse = true
            viewModel.fetchCategories()

            val loadingState = awaitItem()
            assertThat(loadingState.isLoading).isTrue()
            assertThat(loadingState.categories).isEmpty()

            val loadedState = awaitItem()
            assertThat(loadedState.isLoading).isFalse()
            assertThat(loadedState.categories).isEmpty()
            assertThat(loadedState.message).isEqualTo(strRes(R.string.error_network))

            assertThat(cancelAndConsumeRemainingEvents().size).isEqualTo(0)
        }
    }

    @Test
    fun `test category selection, returns effect GoBack`() = coroutineRule.runBlockingTest {
        viewModel.effect.test {
            viewModel.setAction(CategorySelectionAction.OnCategorySelect(Category.Any))

            val goBack = awaitItem()
            assertThat(goBack).isEqualTo(CategorySelectionEffect.GoBack)

            assertThat(cancelAndConsumeRemainingEvents().size).isEqualTo(0)
        }
    }

    @Test
    fun `test search, returns list with one item`() = coroutineRule.runBlockingTest {
        viewModel.fetchCategories()
        viewModel.state.test {
            viewModel.setAction(CategorySelectionAction.OnSearchExecute("animal"))

            awaitItem() // skip state before action

            val searchLoadingState = awaitItem()
            assertThat(searchLoadingState.isLoading).isTrue()
            assertThat(searchLoadingState.categories).hasSize(3)

            val successState = awaitItem()
            assertThat(successState.isLoading).isFalse()
            assertThat(successState.categories).hasSize(1)
            assertThat(
                (successState.categories.first() as Category.Specific).name
            ).contains("animal")

            assertThat(cancelAndConsumeRemainingEvents().size).isEqualTo(0)
        }
    }

    @Test
    fun `invalid test search, returns error`() = coroutineRule.runBlockingTest {
        viewModel.fetchCategories()
        viewModel.state.test {
            viewModel.setAction(CategorySelectionAction.OnSearchExecute("animalgdgdf"))

            awaitItem() // skip state before action

            val searchLoadingState = awaitItem()
            assertThat(searchLoadingState.isLoading).isTrue()
            assertThat(searchLoadingState.categories).hasSize(3)

            val errorState = awaitItem()
            assertThat(errorState.isLoading).isFalse()
            assertThat(errorState.message).isNotNull()
            assertThat(errorState.message).isEqualTo(strRes(R.string.error_not_found))
            assertThat(errorState.categories).isEmpty()

            assertThat(cancelAndConsumeRemainingEvents().size).isEqualTo(0)
        }
    }

    @Test
    fun `valid test search, returns error`() = coroutineRule.runBlockingTest {
        repositoryFake.shouldReturnErrorResponse = true
        viewModel.fetchCategories()

        viewModel.state.test {
            viewModel.setAction(CategorySelectionAction.OnSearchExecute("animal"))

            awaitItem() // skip state before action

            val searchLoadingState = awaitItem()
            assertThat(searchLoadingState.isLoading).isTrue()
            assertThat(searchLoadingState.categories).isEmpty()

            val errorState = awaitItem()
            assertThat(errorState.isLoading).isFalse()
            assertThat(errorState.message).isEqualTo(strRes(R.string.error_not_found))
            assertThat(errorState.categories).isEmpty()

            assertThat(cancelAndConsumeRemainingEvents().size).isEqualTo(0)
        }
    }
}