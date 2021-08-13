package ru.heatalways.chucknorrisfunfacts.presentation.screen.search_joke

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.heatalways.chucknorrisfunfacts.R
import ru.heatalways.chucknorrisfunfacts.data.utils.StringResource
import ru.heatalways.chucknorrisfunfacts.domain.managers.chuck_norris_jokes.ChuckNorrisJokesManager
import ru.heatalways.chucknorrisfunfacts.presentation.base.BaseMviViewModel
import javax.inject.Inject

@HiltViewModel
class SearchJokeViewModel @Inject constructor(
    private val jokesManager: ChuckNorrisJokesManager
): BaseMviViewModel<
        SearchJokeContract.Action,
        SearchJokeContract.State,
        SearchJokeContract.Effect,
        SearchJokeContract.PartialState
>(SearchJokeContract.Reducer) {

    override val initialState get() = SearchJokeContract.State(
        message = StringResource.ByRes(R.string.search_joke_empty_hint)
    )

    override fun handleAction(action: SearchJokeContract.Action) {
        when (action) {
            is SearchJokeContract.Action.OnSearchExecute -> onSearchQueryExecute(action.query)
        }
    }

    private fun onSearchQueryExecute(query: String) {
        viewModelScope.launch {
            reduceState(SearchJokeContract.PartialState.Loading)
            val response = jokesManager.search(query)

            when {
                !response.isOk || response.value == null -> reduceState(
                    SearchJokeContract.PartialState.Message(strRes(
                        response.error?.message
                    ))
                )

                response.value.isEmpty() -> reduceState(
                    SearchJokeContract.PartialState.Message(strRes(
                        R.string.error_not_found
                    ))
                )

                else -> reduceState(
                    SearchJokeContract.PartialState.Jokes(response.value)
                )
            }
        }
    }
}