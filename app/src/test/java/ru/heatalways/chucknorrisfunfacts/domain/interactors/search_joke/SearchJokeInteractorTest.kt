package ru.heatalways.chucknorrisfunfacts.domain.interactors.search_joke

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
class SearchJokeInteractorTest {
    private lateinit var repositoryFake: ChuckNorrisJokesRepositoryFake
    private lateinit var interactor: SearchJokeInteractor

    @Before
    fun setup() {
        repositoryFake = ChuckNorrisJokesRepositoryFake()
        interactor = SearchJokeInteractorImpl(repositoryFake)
    }

    @Test
    fun `search valid query, should return network error message state`() = runBlockingTest {
        repositoryFake.shouldReturnErrorResponse = true

        val responses = interactor.search("hey").toList()

        assertThat(responses).hasSize(2)
        assertThat(responses.first()).isEqualTo(
            SearchJokePartialState.Loading
        )
        assertThat(responses.last()).isEqualTo(
            SearchJokePartialState.Message(strRes(R.string.error_network))
        )
    }

    @Test
    fun `search valid query, should return one item`() = runBlockingTest {
        val responses = interactor.search("hey").toList()

        assertThat(responses).hasSize(2)
        assertThat(responses.first()).isEqualTo(
            SearchJokePartialState.Loading
        )
        assertThat(responses.last()).isEqualTo(
            SearchJokePartialState.Jokes(jokes = listOf(repositoryFake.jokes.first()))
        )
    }
}