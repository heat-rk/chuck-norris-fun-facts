package ru.heatalways.chucknorrisfunfacts.domain.interactors.random_joke.select_category

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import ru.heatalways.chucknorrisfunfacts.R
import ru.heatalways.chucknorrisfunfacts.domain.repositories.chuck_norris_jokes.ChuckNorrisJokesRepositoryFake
import ru.heatalways.chucknorrisfunfacts.domain.utils.strRes
import ru.heatalways.chucknorrisfunfacts.presentation.screen.random_joke.select_category.CategorySelectionPartialState

@ExperimentalCoroutinesApi
class CategorySelectionInteractorTest {
    private lateinit var repositoryFake: ChuckNorrisJokesRepositoryFake
    private lateinit var interactor: CategorySelectionInteractor

    @Before
    fun setup() {
        repositoryFake = ChuckNorrisJokesRepositoryFake()
        interactor = CategorySelectionInteractorImpl(repositoryFake)
    }

    @Test
    fun `fetch categories, returns list with 3 elements`() = runBlockingTest {
        val responses = interactor.fetchCategories().toList()

        assertThat(responses).hasSize(1)
        assertThat(responses.first()).isInstanceOf(
            CategorySelectionPartialState.CategoriesLoaded::class.java
        )
        assertThat((responses.first() as CategorySelectionPartialState.CategoriesLoaded).categories)
            .hasSize(3)
    }

    @Test
    fun `fetch categories, returns error`() = runBlockingTest {
        repositoryFake.shouldReturnErrorResponse = true
        val responses = interactor.fetchCategories().toList()

        assertThat(responses).hasSize(1)
        assertThat(responses.first()).isInstanceOf(
            CategorySelectionPartialState.CategoriesMessage::class.java
        )
        assertThat(responses.first()).isEqualTo(
            CategorySelectionPartialState.CategoriesMessage(strRes(R.string.error_network))
        )
    }

    @Test
    fun `search categories, returns list with 1 element`() = runBlockingTest {
        interactor.fetchCategories().toList()
        val responses = interactor.searchCategories("animal").toList()

        assertThat(responses).hasSize(2)
        assertThat(responses.first()).isEqualTo(CategorySelectionPartialState.CategoriesLoading)
        assertThat(responses.last()).isInstanceOf(
            CategorySelectionPartialState.CategoriesLoaded::class.java
        )
        assertThat((responses.last() as CategorySelectionPartialState.CategoriesLoaded).categories)
            .hasSize(1)
    }

    @Test
    fun `search invalid category, returns error`() = runBlockingTest {
        interactor.fetchCategories().toList()
        val responses = interactor.searchCategories("animalsdgsdg").toList()

        assertThat(responses).hasSize(2)
        assertThat(responses.first()).isEqualTo(CategorySelectionPartialState.CategoriesLoading)
        assertThat(responses.last()).isEqualTo(
            CategorySelectionPartialState.CategoriesMessage(strRes(R.string.error_not_found))
        )
    }

    @Test
    fun `search valid category, returns error`() = runBlockingTest {
        repositoryFake.shouldReturnErrorResponse = true
        interactor.fetchCategories().toList()
        val responses = interactor.searchCategories("animal").toList()

        assertThat(responses).hasSize(2)
        assertThat(responses.first()).isEqualTo(CategorySelectionPartialState.CategoriesLoading)
        assertThat(responses.last()).isEqualTo(
            CategorySelectionPartialState.CategoriesMessage(strRes(R.string.error_not_found))
        )
    }
}