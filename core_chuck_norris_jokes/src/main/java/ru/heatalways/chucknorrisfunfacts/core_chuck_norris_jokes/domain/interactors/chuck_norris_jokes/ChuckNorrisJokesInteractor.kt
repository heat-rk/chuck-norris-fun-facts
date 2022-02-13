package ru.heatalways.chucknorrisfunfacts.core_chuck_norris_jokes.domain.interactors.chuck_norris_jokes

import kotlinx.coroutines.flow.Flow
import ru.heatalways.chucknorrisfunfacts.core.domain.models.Category
import ru.heatalways.chucknorrisfunfacts.core.domain.models.ChuckJoke
import ru.heatalways.chucknorrisfunfacts.common.domain.utils.InteractorEvent
import ru.heatalways.chucknorrisfunfacts.common.general.utils.StringResource
import ru.heatalways.chucknorrisfunfacts.common.general.utils.paging.PagingConfig
import ru.heatalways.chucknorrisfunfacts.common.general.utils.paging.PagingEvent

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