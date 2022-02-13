package ru.heatalways.chucknorrisfunfacts.random_joke.presentation.screen.random_joke.select_category

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import ru.heatalways.chucknorrisfunfacts.common.general.utils.strRes
import ru.heatalways.chucknorrisfunfacts.common.presentation.utils.ScrollState
import ru.heatalways.chucknorrisfunfacts.common.utils.BaseViewModelTest
import ru.heatalways.chucknorrisfunfacts.core.domain.models.Category
import ru.heatalways.chucknorrisfunfacts.core_chuck_norris_jokes.data.repositories.chuck_norris_jokes.ChuckNorrisJokesRepositoryFake
import ru.heatalways.chucknorrisfunfacts.core_chuck_norris_jokes.domain.interactors.chuck_norris_jokes.ChuckNorrisJokesInteractor
import ru.heatalways.chucknorrisfunfacts.core_chuck_norris_jokes.domain.interactors.chuck_norris_jokes.ChuckNorrisJokesInteractorImpl
import ru.heatalways.chucknorrisfunfacts.random_joke.R
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
class CategorySelectionViewModelTest: BaseViewModelTest() {
    private lateinit var repositoryFake: ChuckNorrisJokesRepositoryFake
    private lateinit var interactor: ChuckNorrisJokesInteractor
    private lateinit var viewModel: CategorySelectionViewModel

    @Before
    fun setup() {
        repositoryFake = ChuckNorrisJokesRepositoryFake()
        interactor = ChuckNorrisJokesInteractorImpl(repositoryFake)
        viewModel = CategorySelectionViewModel(
            savedStateHandle = SavedStateHandle(),
            interactor = interactor
        )
    }

    @Test
    fun `test viewModel init, returns 3 categories`() = runTest {
        viewModel.state.test {
            val loadingState = awaitItem()
            assertThat(loadingState.isCategoriesLoading).isTrue()
            assertThat(loadingState.categories).isEmpty()

            val loadedState = awaitItem()
            assertThat(loadedState.isCategoriesLoading).isFalse()
            assertThat(loadedState.categories).hasSize(3)

            assertThat(cancelAndConsumeRemainingEvents().size).isEqualTo(0)
        }
    }

    @Test
    fun `test viewModel init, returns error`() = runTest {
        repositoryFake.shouldReturnErrorResponse = true
        viewModel.state.test {
            val loadingState = awaitItem()
            assertThat(loadingState.isCategoriesLoading).isTrue()
            assertThat(loadingState.categories).isEmpty()

            val loadedState = awaitItem()
            assertThat(loadedState.isCategoriesLoading).isFalse()
            assertThat(loadedState.categories).isEmpty()
            assertThat(loadedState.categoriesMessage).isEqualTo(strRes(R.string.error_network))

            assertThat(cancelAndConsumeRemainingEvents().size).isEqualTo(0)
        }
    }

    @Test
    fun `test category selection, returns effect GoBack`() = runTest {
        viewModel.setAction(CategorySelectionAction.OnCategorySelect(Category.Any))
        // TODO: test navigation in future
    }

    @Test
    fun `test search, returns list with one item`() = runTest {
        viewModel.state.test {
            awaitItem() // skip state before action

            viewModel.setAction(CategorySelectionAction.OnSearchExecute("animal"))

            val initialEmptyState = awaitItem()
            assertThat(initialEmptyState.isCategoriesLoading).isFalse()
            assertThat(initialEmptyState.categories).hasSize(3)

            val searchLoadingState = awaitItem()
            assertThat(searchLoadingState.isCategoriesLoading).isTrue()
            assertThat(searchLoadingState.categories).hasSize(3)

            val successState = awaitItem()
            assertThat(successState.isCategoriesLoading).isFalse()
            assertThat(successState.categories).hasSize(1)
            assertThat(
                (successState.categories.first() as Category.Specific).name
            ).contains("animal")

            val scrollingUpState = awaitItem()
            assertThat(scrollingUpState.scrollState).isEqualTo(ScrollState.ScrollingUp)

            val scrollFinishedState = awaitItem()
            assertThat(scrollFinishedState.scrollState).isEqualTo(ScrollState.Stopped)

            assertThat(cancelAndConsumeRemainingEvents().size).isEqualTo(0)
        }
    }

    @Test
    fun `invalid test search, returns error`() = runTest {
        viewModel.state.test {
            awaitItem() // skip state before action

            viewModel.setAction(CategorySelectionAction.OnSearchExecute("animalgdgdf"))

            val initialEmptyState = awaitItem()
            assertThat(initialEmptyState.isCategoriesLoading).isFalse()
            assertThat(initialEmptyState.categories).hasSize(3)

            val searchLoadingState = awaitItem()
            assertThat(searchLoadingState.isCategoriesLoading).isTrue()
            assertThat(searchLoadingState.categories).hasSize(3)

            val errorState = awaitItem()
            assertThat(errorState.isCategoriesLoading).isFalse()
            assertThat(errorState.categoriesMessage).isNotNull()
            assertThat(errorState.categoriesMessage).isEqualTo(strRes(R.string.error_not_found))
            assertThat(errorState.categories).isEmpty()

            assertThat(cancelAndConsumeRemainingEvents().size).isEqualTo(0)
        }
    }

    @Test
    fun `valid test search, returns error`() = runTest {
        repositoryFake.shouldReturnErrorResponse = true

        viewModel.state.test {
            awaitItem()

            viewModel.setAction(CategorySelectionAction.OnSearchExecute("animal"))

            val initialEmptyState = awaitItem()
            assertThat(initialEmptyState.isCategoriesLoading).isFalse()
            assertThat(initialEmptyState.categories).isEmpty()
            assertThat(initialEmptyState.categoriesMessage).isEqualTo(
                strRes(R.string.error_network)
            )

            val searchLoadingState = awaitItem()
            assertThat(searchLoadingState.isCategoriesLoading).isTrue()
            assertThat(searchLoadingState.categories).isEmpty()

            val errorState = awaitItem()
            assertThat(errorState.isCategoriesLoading).isFalse()
            assertThat(errorState.categoriesMessage).isEqualTo(
                strRes(R.string.error_network)
            )
            assertThat(errorState.categories).isEmpty()

            assertThat(cancelAndConsumeRemainingEvents().size).isEqualTo(0)
        }
    }
}