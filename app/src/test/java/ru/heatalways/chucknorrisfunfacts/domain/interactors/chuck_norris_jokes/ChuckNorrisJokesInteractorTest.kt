package ru.heatalways.chucknorrisfunfacts.domain.interactors.chuck_norris_jokes

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import ru.heatalways.chucknorrisfunfacts.R
import ru.heatalways.chucknorrisfunfacts.domain.repositories.chuck_norris_jokes.Category
import ru.heatalways.chucknorrisfunfacts.domain.repositories.chuck_norris_jokes.ChuckJoke
import ru.heatalways.chucknorrisfunfacts.domain.repositories.chuck_norris_jokes.ChuckNorrisJokesRepositoryFake
import ru.heatalways.chucknorrisfunfacts.domain.utils.InteractorEvent
import ru.heatalways.chucknorrisfunfacts.domain.utils.StringResource
import ru.heatalways.chucknorrisfunfacts.domain.utils.paging.PagingConfig
import ru.heatalways.chucknorrisfunfacts.domain.utils.paging.PagingEvent
import ru.heatalways.chucknorrisfunfacts.domain.utils.strRes
import ru.heatalways.chucknorrisfunfacts.mappers.toEntity

@ExperimentalCoroutinesApi
class ChuckNorrisJokesInteractorTest {
    private lateinit var repositoryFake: ChuckNorrisJokesRepositoryFake
    private lateinit var interactor: ChuckNorrisJokesInteractor

    @Before
    fun setup() {
        repositoryFake = ChuckNorrisJokesRepositoryFake()
        interactor = ChuckNorrisJokesInteractorImpl(repositoryFake)
    }

    @Test
    fun `search valid query, should return network error message state`() = runBlockingTest {
        repositoryFake.shouldReturnErrorResponse = true

        val responses = interactor.search("hey").toList()

        assertThat(responses).hasSize(2)
        assertThat(responses.first()).isEqualTo(InteractorEvent.Loading)
        assertThat(responses.last()).isEqualTo(
            InteractorEvent.Error(strRes(R.string.error_network))
        )
    }

    @Test
    fun `search valid query, should return one item`() = runBlockingTest {
        val responses = interactor.search("hey").toList()

        assertThat(responses).hasSize(2)
        assertThat(responses.first()).isEqualTo(InteractorEvent.Loading)
        assertThat(responses.last()).isEqualTo(
            InteractorEvent.Success(listOf(repositoryFake.jokes.first()))
        )
    }

    @Test
    fun `fetch saved jokes, returns list with one element`() = runBlockingTest {
        repositoryFake.savedJokes.add(repositoryFake.jokes.first().toEntity())
        val responses = interactor.fetchJokes(PagingConfig.Initial).toList()

        assertThat(responses).hasSize(1)
        assertThat(responses.first()).isEqualTo(
            PagingEvent.Loaded<ChuckJoke, StringResource>(
                listOf(repositoryFake.jokes.first())
            )
        )
    }

    @Test
    fun `fetch saved jokes, returns empty list with message`() = runBlockingTest {
        val responses = interactor.fetchJokes(PagingConfig.Initial).toList()

        assertThat(responses).hasSize(1)
        assertThat(responses.first()).isEqualTo(
            PagingEvent.LoadError<ChuckJoke, StringResource>(
                strRes(R.string.random_joke_empty_history)
            )
        )
    }

    @Test
    fun `fetch random joke from category ANY, returns one joke`() = runBlockingTest {
        val responses = interactor.fetchRandomJoke(Category.Any).toList()

        assertThat(responses).hasSize(2)
        assertThat(responses.first()).isEqualTo(InteractorEvent.Loading)
        assertThat(responses.last()).isInstanceOf(InteractorEvent.Success::class.java)
    }

    @Test
    fun `fetch random joke from category CAREER, returns one joke`() = runBlockingTest {
        val responses = interactor
            .fetchRandomJoke(Category.Specific("career")).toList()

        assertThat(responses).hasSize(2)
        assertThat(responses.first()).isEqualTo(InteractorEvent.Loading)
        assertThat(responses.last()).isInstanceOf(
            InteractorEvent.Success::class.java
        )
        assertThat((responses.last() as InteractorEvent.Success).body.categories)
            .contains(Category.Specific("career"))
    }

    @Test
    fun `fetch random joke from category BLABLABLA, returns error`() = runBlockingTest {
        val responses = interactor
            .fetchRandomJoke(Category.Specific("blablabla")).toList()

        assertThat(responses).hasSize(2)
        assertThat(responses.first()).isEqualTo(InteractorEvent.Loading)
        assertThat(responses.last()).isInstanceOf(InteractorEvent.Error::class.java)
    }

    @Test
    fun `fetch random joke from category ANY, returns error`() = runBlockingTest {
        repositoryFake.shouldReturnErrorResponse = true
        val responses = interactor
            .fetchRandomJoke(Category.Any).toList()

        assertThat(responses).hasSize(2)
        assertThat(responses.first()).isEqualTo(InteractorEvent.Loading)
        assertThat(responses.last()).isInstanceOf(InteractorEvent.Error::class.java)
    }

    @Test
    fun `fetch categories, returns list with 3 elements`() = runBlockingTest {
        val responses = interactor.fetchCategories().toList()

        assertThat(responses).hasSize(2)
        assertThat(responses.first()).isEqualTo(InteractorEvent.Loading)
        assertThat(responses.last()).isInstanceOf(
            InteractorEvent.Success::class.java
        )
        assertThat((responses.last() as InteractorEvent.Success).body)
            .hasSize(3)
    }

    @Test
    fun `fetch categories, returns error`() = runBlockingTest {
        repositoryFake.shouldReturnErrorResponse = true
        val responses = interactor.fetchCategories().toList()

        assertThat(responses).hasSize(2)
        assertThat(responses.first()).isEqualTo(InteractorEvent.Loading)
        assertThat(responses.last()).isInstanceOf(InteractorEvent.Error::class.java)
        assertThat(responses.last()).isEqualTo(
            InteractorEvent.Error(strRes(R.string.error_network))
        )
    }

    @Test
    fun `search categories, returns list with 1 element`() = runBlockingTest {
        interactor.fetchCategories().toList()
        val responses = interactor.searchCategories("animal").toList()

        assertThat(responses).hasSize(2)
        assertThat(responses.first()).isEqualTo(InteractorEvent.Loading)
        assertThat(responses.last()).isInstanceOf(InteractorEvent.Success::class.java)
        assertThat((responses.last() as InteractorEvent.Success).body)
            .hasSize(1)
    }

    @Test
    fun `search invalid category, returns error`() = runBlockingTest {
        interactor.fetchCategories().toList()
        val responses = interactor.searchCategories("animalsdgsdg").toList()

        assertThat(responses).hasSize(2)
        assertThat(responses.first()).isEqualTo(InteractorEvent.Loading)
        assertThat(responses.last()).isEqualTo(
            InteractorEvent.Error(strRes(R.string.error_not_found))
        )
    }

    @Test
    fun `search valid category, returns error`() = runBlockingTest {
        repositoryFake.shouldReturnErrorResponse = true
        interactor.fetchCategories().toList()
        val responses = interactor.searchCategories("animal").toList()

        assertThat(responses).hasSize(2)
        assertThat(responses.first()).isEqualTo(InteractorEvent.Loading)
        assertThat(responses.last()).isEqualTo(
            InteractorEvent.Error(strRes(R.string.error_network))
        )
    }
}