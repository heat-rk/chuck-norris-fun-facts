package ru.heatalways.chucknorrisfunfacts.presentation.screen.random_joke

import ru.heatalways.chucknorrisfunfacts.domain.models.Category
import ru.heatalways.chucknorrisfunfacts.domain.models.ChuckJoke
import ru.heatalways.chucknorrisfunfacts.presentation.util.SnackbarState
import ru.heatalways.chucknorrisfunfacts.core.models.StringResource
import ru.heatalways.chucknorrisfunfacts.presentation.util.ToastState
import ru.heatalways.chucknorrisfunfacts.core.base.MviState
import ru.heatalways.chucknorrisfunfacts.presentation.util.ScrollState

data class RandomJokeViewState(
    val isLoading: Boolean = false,
    val isJokeLoading: Boolean = false,
    val message: StringResource? = null,
    val jokes: List<ChuckJoke> = emptyList(),
    val category: Category = Category.Any,
    val scrollState: ScrollState = ScrollState.Stopped,
    val toastState: ToastState = ToastState.Hidden,
    val snackbarState: SnackbarState = SnackbarState.Hidden
): MviState