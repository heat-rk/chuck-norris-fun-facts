package ru.heatalways.chucknorrisfunfacts.domain.interactors.chuck_norris_jokes

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import ru.heatalways.chucknorrisfunfacts.domain.repositories.chuck_norris_jokes.Category
import ru.heatalways.chucknorrisfunfacts.domain.repositories.chuck_norris_jokes.ChuckJoke
import ru.heatalways.chucknorrisfunfacts.domain.utils.InteractorEvent
import ru.heatalways.chucknorrisfunfacts.domain.utils.StringResource
import ru.heatalways.chucknorrisfunfacts.domain.utils.paging.PagingConfig
import ru.heatalways.chucknorrisfunfacts.domain.utils.paging.PagingEvent
import ru.heatalways.chucknorrisfunfacts.presentation.screen.random_joke.RandomJokePartialState
import ru.heatalways.chucknorrisfunfacts.presentation.screen.random_joke.select_category.CategorySelectionPartialState

interface ChuckNorrisJokesInteractor {
    fun search(query: String): Flow<InteractorEvent<List<ChuckJoke>>>

    fun removeSavedJokes(): Flow<InteractorEvent<Unit>>

    fun fetchJokes(pagingConfig: PagingConfig):
            Flow<PagingEvent<ChuckJoke, StringResource>>

    fun fetchRandomJoke(category: Category): Flow<InteractorEvent<ChuckJoke>>

    fun restoreTrashJokes(): Flow<InteractorEvent<List<ChuckJoke>>>

    fun fetchCategories(): Flow<InteractorEvent<List<Category>>>

    fun searchCategories(query: String): Flow<InteractorEvent<List<Category>>>
}