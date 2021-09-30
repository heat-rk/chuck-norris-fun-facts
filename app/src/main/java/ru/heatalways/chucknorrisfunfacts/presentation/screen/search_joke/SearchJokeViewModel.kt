package ru.heatalways.chucknorrisfunfacts.presentation.screen.search_joke

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.heatalways.chucknorrisfunfacts.R
import ru.heatalways.chucknorrisfunfacts.domain.interactors.chuck_norris_jokes.ChuckNorrisJokesInteractor
import ru.heatalways.chucknorrisfunfacts.domain.utils.InteractorEvent
import ru.heatalways.chucknorrisfunfacts.domain.utils.StringResource
import ru.heatalways.chucknorrisfunfacts.presentation.base.BaseMviViewModel
import javax.inject.Inject

@HiltViewModel
class SearchJokeViewModel @Inject constructor(
    private val interactor: ChuckNorrisJokesInteractor,
    private val savedStateHandle: SavedStateHandle
): BaseMviViewModel<
        SearchJokeAction,
        SearchJokeViewState,
        SearchJokePartialState
>(SearchJokeStateReducer) {

    override val initialState get() = SearchJokeViewState(
        jokesMessage = StringResource.ByRes(R.string.search_joke_empty_hint)
    )

    init {
        handleSavedData()
    }

    private fun handleSavedData() {
        savedStateHandle.get<String?>(SAVED_QUERY)?.let { query ->
            interactor.search(query)
                .onEach {
                    when (it) {
                        is InteractorEvent.Error ->
                            reduceState(SearchJokePartialState.JokesMessage(
                                it.message
                            ))

                        is InteractorEvent.Loading ->
                            reduceState(SearchJokePartialState.JokesLoading)

                        is InteractorEvent.Success ->
                            reduceState(SearchJokePartialState.JokesLoaded(
                                it.body
                            ))
                    }
                }
                .launchIn(viewModelScope)
        }
    }

    override fun handleAction(action: SearchJokeAction) {
        when (action) {
            is SearchJokeAction.OnSearchExecute ->
                interactor.search(action.query)
                    .onEach {
                        when (it) {
                            is InteractorEvent.Error ->
                                reduceState(SearchJokePartialState.JokesMessage(
                                    it.message
                                ))

                            is InteractorEvent.Loading ->
                                reduceState(SearchJokePartialState.JokesLoading)

                            is InteractorEvent.Success -> {
                                reduceState(SearchJokePartialState.JokesLoaded(
                                    it.body
                                ))
                                scrollUp()
                            }
                        }
                    }
                    .launchIn(viewModelScope)
                    .also { savedStateHandle.set(SAVED_QUERY, action.query) }
        }
    }

    private fun scrollUp() {
        flow {
            emit(SearchJokePartialState.ScrollUp(true))
            emit(SearchJokePartialState.ScrollUp(false))
        }
            .onEach { reduceState(it) }
            .launchIn(viewModelScope)
    }

    companion object {
        private const val SAVED_QUERY = "screen.search_joke.saved_query"
    }
}