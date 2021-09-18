package ru.heatalways.chucknorrisfunfacts.presentation.screen.random_joke.select_category

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
import ru.heatalways.chucknorrisfunfacts.domain.utils.strRes
import ru.heatalways.chucknorrisfunfacts.utils.BaseViewModelTest
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
            onSelect = {},
            savedStateHandle = SavedStateHandle(),
            interactor = interactor,
            router = Router()
        )
        viewModel.resetState()
    }

    @Test
    fun `test viewModel init, returns 3 categories`() = coroutineRule.runBlockingTest {
        viewModel.state.test {
            viewModel.fetchCategories()

            val loadingState = awaitItem()
            assertThat(loadingState.isCategoriesLoading).isTrue()
            assertThat(loadingState.categories).isEmpty()

            val loadedState = awaitItem()
            assertThat(loadedState.isCategoriesLoading).isFalse()
            assertThat(loadedState.categories).hasSize(3)
        }
    }

    @Test
    fun `test viewModel init, returns error`() = coroutineRule.runBlockingTest {
        viewModel.state.test {
            repositoryFake.shouldReturnErrorResponse = true
            viewModel.fetchCategories()

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
    fun `test category selection, returns effect GoBack`() = coroutineRule.runBlockingTest {
        viewModel.setAction(CategorySelectionAction.OnCategorySelect(Category.Any))
        // TODO: test navigation in future
    }

    @Test
    fun `test search, returns list with one item`() = coroutineRule.runBlockingTest {
        viewModel.fetchCategories()
        viewModel.state.test {
            viewModel.setAction(CategorySelectionAction.OnSearchExecute("animal"))

            awaitItem() // skip state before action

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
            assertThat(scrollingUpState.isScrollingUp).isTrue()

            val scrollFinishedState = awaitItem()
            assertThat(scrollFinishedState.isScrollingUp).isFalse()

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
    fun `valid test search, returns error`() = coroutineRule.runBlockingTest {
        repositoryFake.shouldReturnErrorResponse = true
        viewModel.fetchCategories()

        viewModel.state.test {
            viewModel.setAction(CategorySelectionAction.OnSearchExecute("animal"))

            awaitItem() // skip state before action

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