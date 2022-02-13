package ru.heatalways.chucknorrisfunfacts.random_joke.presentation.screen.random_joke

import ru.heatalways.chucknorrisfunfacts.core.domain.models.Category
import ru.heatalways.chucknorrisfunfacts.core.domain.models.ChuckJoke
import ru.heatalways.chucknorrisfunfacts.common.general.utils.StringResource
import ru.heatalways.chucknorrisfunfacts.common.presentation.utils.ScrollState
import ru.heatalways.chucknorrisfunfacts.common.presentation.utils.SnackbarState
import ru.heatalways.chucknorrisfunfacts.common.presentation.utils.ToastState

internal sealed class RandomJokePartialState {
    data class Toast(val toastState: ToastState): RandomJokePartialState()
    data class Snackbar(val snackbarState: SnackbarState): RandomJokePartialState()
    data class Scroll(val scrollState: ScrollState): RandomJokePartialState()

    object JokeLoading: RandomJokePartialState()
    data class JokeLoaded(val joke: ChuckJoke?): RandomJokePartialState()

    object JokesLoading: RandomJokePartialState()
    data class JokesLoaded(val jokes: List<ChuckJoke>): RandomJokePartialState()
    object JokesUpdating: RandomJokePartialState()
    data class JokesUpdated(val jokes: List<ChuckJoke>): RandomJokePartialState()
    data class JokesMessage(val message: StringResource): RandomJokePartialState()

    data class CategorySelected(val category: Category): RandomJokePartialState()
}