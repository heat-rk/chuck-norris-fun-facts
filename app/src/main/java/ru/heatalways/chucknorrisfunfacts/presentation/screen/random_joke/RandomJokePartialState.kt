package ru.heatalways.chucknorrisfunfacts.presentation.screen.random_joke

import ru.heatalways.chucknorrisfunfacts.domain.models.Category
import ru.heatalways.chucknorrisfunfacts.domain.models.ChuckJoke
import ru.heatalways.chucknorrisfunfacts.presentation.util.SnackbarState
import ru.heatalways.chucknorrisfunfacts.domain.utils.StringResource
import ru.heatalways.chucknorrisfunfacts.presentation.util.ScrollState
import ru.heatalways.chucknorrisfunfacts.presentation.util.ToastState

sealed class RandomJokePartialState {
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