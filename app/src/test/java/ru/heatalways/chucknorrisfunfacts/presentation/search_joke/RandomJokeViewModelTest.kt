package ru.heatalways.chucknorrisfunfacts.presentation.search_joke

import com.github.terrakok.cicerone.Router
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import ru.heatalways.chucknorrisfunfacts.data.entities.Category
import ru.heatalways.chucknorrisfunfacts.domain.managers.ChuckNorrisJokesManagerFake
import ru.heatalways.chucknorrisfunfacts.presentation.screen.random_joke.RandomJokeState
import ru.heatalways.chucknorrisfunfacts.presentation.screen.random_joke.RandomJokeViewModel
import ru.heatalways.chucknorrisfunfacts.utils.BaseViewModelTest
import ru.heatalways.chucknorrisfunfacts.utils.getOrAwaitValueTest

@ExperimentalCoroutinesApi
class RandomJokeViewModelTest: BaseViewModelTest() {
    private val fakeManager =  ChuckNorrisJokesManagerFake()
    private val router = Router()
    private lateinit var viewModel: RandomJokeViewModel

    @Before
    fun setup() {
        viewModel = RandomJokeViewModel(fakeManager, router)
    }

    @Test
    fun `fetch joke from ANY category, returns list with new element`() {
        val previousValue = viewModel.state.getOrAwaitValueTest()

        viewModel.fetchRandomJoke(Category.Any)

        val value = viewModel.state.getOrAwaitValueTest()

        Truth.assertThat(value).isInstanceOf(RandomJokeState.Loaded::class.java)
        if (value is RandomJokeState.Loaded) {
            Truth.assertThat(value.jokes).hasSize(1)
        }
    }
}