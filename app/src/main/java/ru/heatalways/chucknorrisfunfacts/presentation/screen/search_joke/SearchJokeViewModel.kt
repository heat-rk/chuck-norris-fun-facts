package ru.heatalways.chucknorrisfunfacts.presentation.screen.search_joke

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.heatalways.chucknorrisfunfacts.R
import ru.heatalways.chucknorrisfunfacts.domain.interactors.search_joke.*
import ru.heatalways.chucknorrisfunfacts.domain.utils.StringResource
import ru.heatalways.chucknorrisfunfacts.presentation.base.BaseMviViewModel
import javax.inject.Inject

@HiltViewModel
class SearchJokeViewModel @Inject constructor(
    private val searchJokeInteractor: SearchJokeInteractor,
    private val savedStateHandle: SavedStateHandle
): BaseMviViewModel<
        SearchJokeAction,
        SearchJokeViewState,
        SearchJokeViewEffect,
        SearchJokePartialState
>(SearchJokeStateReducer) {

    override val initialState get() = SearchJokeViewState(
        message = StringResource.ByRes(R.string.search_joke_empty_hint)
    )

    init {
        handleSavedData()
    }

    fun handleSavedData() {
        savedStateHandle.get<String?>(SAVED_QUERY)?.let { query ->
            searchJokeInteractor.search(query)
                .onEach { reduceState(it) }
                .launchIn(viewModelScope)
        }
    }

    override fun handleAction(action: SearchJokeAction) {
        when (action) {
            is SearchJokeAction.OnSearchExecute ->
                searchJokeInteractor.search(action.query)
                    .onEach {
                        reduceState(it)

                        if (it is SearchJokePartialState.Jokes)
                            setEffect(SearchJokeViewEffect.ScrollUp)
                    }
                    .launchIn(viewModelScope)
                    .also { savedStateHandle.set(SAVED_QUERY, action.query) }
        }
    }

    companion object {
        private const val SAVED_QUERY = "screen.search_joke.saved_query"
    }
}