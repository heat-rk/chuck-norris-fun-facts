package ru.heatalways.chucknorrisfunfacts.domain.interactors.chuck_norris_jokes

import kotlinx.coroutines.flow.Flow
import ru.heatalways.chucknorrisfunfacts.domain.models.Category
import ru.heatalways.chucknorrisfunfacts.domain.models.ChuckJoke
import ru.heatalways.chucknorrisfunfacts.domain.utils.InteractorEvent
import ru.heatalways.chucknorrisfunfacts.domain.utils.StringResource
import ru.heatalways.chucknorrisfunfacts.domain.utils.paging.PagingConfig
import ru.heatalways.chucknorrisfunfacts.domain.utils.paging.PagingEvent

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