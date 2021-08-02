package ru.heatalways.chucknorrisfunfacts.presentation.screen.search_joke

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.heatalways.chucknorrisfunfacts.domain.managers.chuck_norris_jokes.ChuckNorrisJokesManager
import ru.heatalways.chucknorrisfunfacts.presentation.base.BaseMviViewModel
import javax.inject.Inject

@HiltViewModel
class SearchJokeViewModel @Inject constructor(
    private val jokesManager: ChuckNorrisJokesManager
): BaseMviViewModel<SearchJokeContract.Action, SearchJokeContract.State, SearchJokeContract.Effect>() {

    override val initialState get() = SearchJokeContract.State.Default

    override fun handleAction(action: SearchJokeContract.Action) {
        when (action) {
            is SearchJokeContract.Action.OnSearchExecute -> onSearchQueryExecute(action.query)
        }
    }

    private fun onSearchQueryExecute(query: String) {
        viewModelScope.launch {
            stateWhile(SearchJokeContract.State.Loading) {
                val response = jokesManager.search(query)

                when {
                    !response.isOk || response.value == null ->
                        SearchJokeContract.State.Error(response.error?.message)

                    response.value.isEmpty() ->
                        SearchJokeContract.State.Empty

                    else ->
                        SearchJokeContract.State.Loaded(response.value)
                }
            }
        }
    }
}