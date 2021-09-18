package ru.heatalways.chucknorrisfunfacts.presentation.screen.random_joke

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.github.terrakok.cicerone.Router
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import ru.heatalways.chucknorrisfunfacts.R
import ru.heatalways.chucknorrisfunfacts.domain.interactors.chuck_norris_jokes.ChuckNorrisJokesInteractor
import ru.heatalways.chucknorrisfunfacts.domain.repositories.chuck_norris_jokes.Category
import ru.heatalways.chucknorrisfunfacts.domain.repositories.chuck_norris_jokes.ChuckJoke
import ru.heatalways.chucknorrisfunfacts.domain.utils.*
import ru.heatalways.chucknorrisfunfacts.domain.utils.paging.PagingConfig
import ru.heatalways.chucknorrisfunfacts.domain.utils.paging.PagingEvent
import ru.heatalways.chucknorrisfunfacts.presentation.base.BaseMviViewModel
import ru.heatalways.chucknorrisfunfacts.presentation.screen.random_joke.select_category.CategorySelectionFragment
import javax.inject.Inject

@HiltViewModel
class RandomJokeViewModel @Inject constructor(
    private val interactor: ChuckNorrisJokesInteractor,
    private val savedStateHandle: SavedStateHandle,
    private val router: Router
): BaseMviViewModel<
        RandomJokeAction,
        RandomJokeViewState,
        RandomJokePartialState
>(RandomJokeStateReducer) {

    override val initialState get() = RandomJokeViewState(isLoading = true)

    init {
        handleSavedState()
        fetchJokes()
    }

    private fun handleSavedState() {
        savedStateHandle.get<Category?>(SAVED_SELECTED_CATEGORY)?.let { category ->
            reduceState(RandomJokePartialState.CategorySelected(category))
        }
    }

    fun fetchJokes() {
        interactor.fetchJokes(PagingConfig.Initial)
            .onEach { handlePagingEvent(it) }
            .launchIn(viewModelScope)
    }

    override fun handleAction(action: RandomJokeAction) {
        when (action) {
            is RandomJokeAction.OnCategorySelectionButtonClick ->
                navigateToCategorySelectionScreen()

            is RandomJokeAction.OnRandomJokeRequest ->
                fetchRandomJoke()

            is RandomJokeAction.RemoveAll ->
                removeSavedJokes()

            is RandomJokeAction.OnNextPage ->
                nextPage()
        }
    }

    fun selectCategory(category: Category) {
        reduceState(RandomJokePartialState.CategorySelected(category))
        savedStateHandle.set(SAVED_SELECTED_CATEGORY, category)
    }

    private fun navigateToCategorySelectionScreen() {
        router.navigateTo(CategorySelectionFragment.getScreen { category ->
            selectCategory(category)
        })
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
                            callback = ::restoreRemovedJokes,
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
        reduceState(RandomJokePartialState.ScrollUp(true))
        reduceState(RandomJokePartialState.ScrollUp(false))
    }

    private fun showToast(message: StringResource) {
        reduceState(RandomJokePartialState.Toast(ToastState.Shown(message)))
        reduceState(RandomJokePartialState.Toast(ToastState.Hidden))
    }

    private fun showSnackbar(
        message: StringResource,
        buttonText: StringResource,
        callback: () -> Unit = {},
        duration: Long
    ) {
        flow {
            emit(RandomJokePartialState.Snackbar(SnackbarState.Shown(
                message = message,
                buttonText = buttonText,
                buttonCallback = callback
            )))

            delay(duration)

            emit(RandomJokePartialState.Snackbar(SnackbarState.Hidden))
        }
            .onEach { reduceState(it) }
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
            .onStart {
                reduceState(RandomJokePartialState.Snackbar(SnackbarState.Hidden))
            }
            .launchIn(viewModelScope)
    }

    companion object {
        private const val SAVED_SELECTED_CATEGORY =
            "screen.random_joke.saved_selected_category"
    }
}