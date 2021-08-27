package ru.heatalways.chucknorrisfunfacts.domain.interactors.random_joke

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import ru.heatalways.chucknorrisfunfacts.R
import ru.heatalways.chucknorrisfunfacts.domain.repositories.chuck_norris_jokes.Category
import ru.heatalways.chucknorrisfunfacts.domain.repositories.chuck_norris_jokes.ChuckNorrisJokesRepositoryFake
import ru.heatalways.chucknorrisfunfacts.domain.utils.strRes
import ru.heatalways.chucknorrisfunfacts.mappers.toEntity

@ExperimentalCoroutinesApi
class RandomJokeInteractorTest {
    private lateinit var repositoryFake: ChuckNorrisJokesRepositoryFake
    private lateinit var interactor: RandomJokeInteractor

    @Before
    fun setup() {
        repositoryFake = ChuckNorrisJokesRepositoryFake()
        interactor = RandomJokeInteractorImpl(repositoryFake)
    }

    @Test
    fun `fetch saved jokes, returns list with one element`() = runBlockingTest {
        repositoryFake.savedJokes.add(repositoryFake.jokes.first().toEntity())
        val responses = interactor.fetchJokes().toList()

        assertThat(responses).hasSize(1)
        assertThat(responses.first()).isEqualTo(
            RandomJokePartialState.JokesLoaded(listOf(repositoryFake.jokes.first()))
        )
    }

    @Test
    fun `fetch saved jokes, returns empty list with message`() = runBlockingTest {
        val responses = interactor.fetchJokes().toList()

        assertThat(responses).hasSize(1)
        assertThat(responses.first()).isEqualTo(
            RandomJokePartialState.Message(strRes(R.string.random_joke_empty_history))
        )
    }

    @Test
    fun `fetch random joke from category ANY, returns one joke`() = runBlockingTest {
        val responses = interactor.fetchRandomJoke(Category.Any).toList()

        assertThat(responses).hasSize(2)
        assertThat(responses.first()).isEqualTo(RandomJokePartialState.JokeLoading)
        assertThat(responses.last()).isInstanceOf(
            RandomJokePartialState.JokeLoaded::class.java
        )
    }

    @Test
    fun `fetch random joke from category CAREER, returns one joke`() = runBlockingTest {
        val responses = interactor
            .fetchRandomJoke(Category.Specific("career")).toList()

        assertThat(responses).hasSize(2)
        assertThat(responses.first()).isEqualTo(RandomJokePartialState.JokeLoading)
        assertThat(responses.last()).isInstanceOf(
            RandomJokePartialState.JokeLoaded::class.java
        )
        assertThat((responses.last() as RandomJokePartialState.JokeLoaded).joke.categories)
            .contains(Category.Specific("career"))
    }

    @Test
    fun `fetch random joke from category BLABLABLA, returns error`() = runBlockingTest {
        val responses = interactor
            .fetchRandomJoke(Category.Specific("blablabla")).toList()

        assertThat(responses).hasSize(2)
        assertThat(responses.first()).isEqualTo(RandomJokePartialState.JokeLoading)
        assertThat(responses.last()).isInstanceOf(
            RandomJokePartialState.JokeLoadingError::class.java
        )
    }

    @Test
    fun `fetch random joke from category ANY, returns error`() = runBlockingTest {
        repositoryFake.shouldReturnErrorResponse = true
        val responses = interactor
            .fetchRandomJoke(Category.Any).toList()

        assertThat(responses).hasSize(2)
        assertThat(responses.first()).isEqualTo(RandomJokePartialState.JokeLoading)
        assertThat(responses.last()).isInstanceOf(
            RandomJokePartialState.JokeLoadingError::class.java
        )
    }
}