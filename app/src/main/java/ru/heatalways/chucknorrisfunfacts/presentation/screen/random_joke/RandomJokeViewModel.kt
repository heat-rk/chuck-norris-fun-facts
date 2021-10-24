package ru.heatalways.chucknorrisfunfacts.presentation.screen.random_joke

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import ru.heatalways.chucknorrisfunfacts.R
import ru.heatalways.chucknorrisfunfacts.core.utils.StringResource
import ru.heatalways.chucknorrisfunfacts.core.utils.strRes
import ru.heatalways.chucknorrisfunfacts.domain.interactors.chuck_norris_jokes.ChuckNorrisJokesInteractor
import ru.heatalways.chucknorrisfunfacts.domain.models.Category
import ru.heatalways.chucknorrisfunfacts.domain.models.ChuckJoke
import ru.heatalways.chucknorrisfunfacts.domain.utils.InteractorEvent
import ru.heatalways.chucknorrisfunfacts.domain.utils.paging.PagingConfig
import ru.heatalways.chucknorrisfunfacts.domain.utils.paging.PagingEvent
import ru.heatalways.chucknorrisfunfacts.extensions.flowTimer
import ru.heatalways.chucknorrisfunfacts.extensions.mergeWith
import ru.heatalways.chucknorrisfunfacts.presentation.base.MviViewModel
import ru.heatalways.chucknorrisfunfacts.presentation.util.ScrollState
import ru.heatalways.chucknorrisfunfacts.presentation.util.SnackbarState
import ru.heatalways.chucknorrisfunfacts.presentation.util.ToastState
import javax.inject.Inject

@HiltViewModel
class RandomJokeViewModel @Inject constructor(
    private val interactor: ChuckNorrisJokesInteractor
): MviViewModel<
        RandomJokeAction,
        RandomJokeViewState,
        RandomJokePartialState
>(RandomJokeStateReducer) {

    override val initialState get() = RandomJokeViewState(isLoading = true)

    init {
        fetchJokes()
    }

    fun fetchJokes() {
        interactor.fetchJokes(PagingConfig.Initial)
            .onEach { handlePagingEvent(it) }
            .launchIn(viewModelScope)
    }

    override fun handleAction(action: RandomJokeAction) {
        when (action) {
            is RandomJokeAction.SelectCategory ->
                navigateToCategorySelectionScreen()

            is RandomJokeAction.RequestRandomJoke ->
                fetchRandomJoke()

            is RandomJokeAction.ToolbarItemSelect ->
                when (action.itemId) {
                    R.id.removeAll -> removeSavedJokes()
                }

            is RandomJokeAction.NextPage ->
                nextPage()

            is RandomJokeAction.RestoreJokes ->
                restoreRemovedJokes()
        }
    }

    fun selectCategory(category: Category) {
        reduceState(RandomJokePartialState.CategorySelected(category))
    }

    private fun navigateToCategorySelectionScreen() {
        navigator {
            navigate(R.id.action_randomJokeFragment_to_categorySelectionFragment)
        }
    }

    private fun fetchRandomJoke() {
        interactor.fetchRandomJoke(state.value.category)
            .onEach {
                when (it) {
                    is InteractorEvent.Loading -> {
                        reduceState(RandomJokePartialState.JokeLoading)
                    }

                    is InteractorEvent.Error -> {
                        reduceState(RandomJokePartialState.JokeLoaded(null))
                        showToast(it.message)
                    }

                    is InteractorEvent.Success -> {
                        reduceState(RandomJokePartialState.JokeLoaded(it.body))
                        scrollUp()
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun removeSavedJokes() {
        interactor.removeSavedJokes()
            .onEach {
                when (it) {
                    is InteractorEvent.Loading ->
                        reduceState(RandomJokePartialState.JokesLoading)

                    is InteractorEvent.Error ->
                        showToast(it.message)

                    is InteractorEvent.Success -> {
                        scrollUp()
                        reduceState(RandomJokePartialState.JokesMessage(
                            strRes(R.string.random_joke_empty_history)
                        ))
                        showSnackbar(
                            message = strRes(R.string.remove_all_success),
                            buttonText = strRes(R.string.undo),
                            duration = 5000
                        )
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun nextPage() {
        interactor.fetchJokes(PagingConfig.Update(
            itemsCount = state.value.jokes.size
        ))
            .onEach { handlePagingEvent(it) }
            .launchIn(viewModelScope)
    }

    private fun handlePagingEvent(
        pagingEvent: PagingEvent<ChuckJoke, StringResource>
    ) {
        when (pagingEvent) {
            is PagingEvent.LoadError ->
                reduceState(RandomJokePartialState.JokesMessage(pagingEvent.error))

            is PagingEvent.Loaded ->
                reduceState(RandomJokePartialState.JokesLoaded(pagingEvent.items))

            is PagingEvent.UpdateError -> Unit

            is PagingEvent.Updated ->
                reduceState(RandomJokePartialState.JokesUpdated(pagingEvent.items))

            is PagingEvent.Updating ->
                reduceState(RandomJokePartialState.JokesUpdating)
        }
    }

    private fun scrollUp() {
        reduceState(RandomJokePartialState.Scroll(ScrollState.ScrollingUp))
        reduceState(RandomJokePartialState.Scroll(ScrollState.Stopped))
    }

    private fun showToast(message: StringResource) {
        reduceState(RandomJokePartialState.Toast(ToastState.Shown(message)))
        reduceState(RandomJokePartialState.Toast(ToastState.Hidden))
    }

    private fun showSnackbar(
        message: StringResource,
        buttonText: StringResource,
        duration: Long
    ) {
        flowTimer(duration)
            .mergeWith(action.filter { it is RandomJokeAction.RestoreJokes })
            .take(1)
            .map {
                RandomJokePartialState.Snackbar(SnackbarState.Hidden)
            }
            .onStart {
                emit(RandomJokePartialState.Snackbar(
                    SnackbarState.Shown(
                        message = message,
                        buttonText = buttonText
                    )))
            }
            .onEach {
                reduceState(it)
            }
            .launchIn(viewModelScope)
    }

    private fun restoreRemovedJokes() {
        interactor.restoreTrashJokes()
            .onEach {
                when (it) {
                    is InteractorEvent.Loading ->
                        reduceState(RandomJokePartialState.JokesLoading)

                    is InteractorEvent.Error ->
                        reduceState(RandomJokePartialState.JokesMessage(it.message))

                    is InteractorEvent.Success ->
                        reduceState(RandomJokePartialState.JokesLoaded(it.body))
                }
            }
            .launchIn(viewModelScope)
    }
}