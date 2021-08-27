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
            CategorySelectionPartialState.Categories::class.java
        )
        assertThat((responses.first() as CategorySelectionPartialState.Categories).categories)
            .hasSize(3)
    }

    @Test
    fun `fetch categories, returns error`() = runBlockingTest {
        repositoryFake.shouldReturnErrorResponse = true
        val responses = interactor.fetchCategories().toList()

        assertThat(responses).hasSize(1)
        assertThat(responses.first()).isInstanceOf(
            CategorySelectionPartialState.Message::class.java
        )
        assertThat(responses.first()).isEqualTo(
            CategorySelectionPartialState.Message(strRes(R.string.error_network))
        )
    }

    @Test
    fun `search categories, returns list with 1 element`() = runBlockingTest {
        interactor.fetchCategories().toList()
        val responses = interactor.searchCategories("animal").toList()

        assertThat(responses).hasSize(2)
        assertThat(responses.first()).isEqualTo(CategorySelectionPartialState.Loading)
        assertThat(responses.last()).isInstanceOf(
            CategorySelectionPartialState.Categories::class.java
        )
        assertThat((responses.last() as CategorySelectionPartialState.Categories).categories)
            .hasSize(1)
    }

    @Test
    fun `search invalid category, returns error`() = runBlockingTest {
        interactor.fetchCategories().toList()
        val responses = interactor.searchCategories("animalsdgsdg").toList()

        assertThat(responses).hasSize(2)
        assertThat(responses.first()).isEqualTo(CategorySelectionPartialState.Loading)
        assertThat(responses.last()).isEqualTo(
            CategorySelectionPartialState.Message(strRes(R.string.error_not_found))
        )
    }

    @Test
    fun `search valid category, returns error`() = runBlockingTest {
        repositoryFake.shouldReturnErrorResponse = true
        interactor.fetchCategories().toList()
        val responses = interactor.searchCategories("animal").toList()

        assertThat(responses).hasSize(2)
        assertThat(responses.first()).isEqualTo(CategorySelectionPartialState.Loading)
        assertThat(responses.last()).isEqualTo(
            CategorySelectionPartialState.Message(strRes(R.string.error_not_found))
        )
    }
}