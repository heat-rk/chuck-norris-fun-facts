package ru.heatalways.chucknorrisfunfacts.random_joke.presentation.screen.random_joke

import ru.heatalways.chucknorrisfunfacts.core.domain.models.Category
import ru.heatalways.chucknorrisfunfacts.core.domain.models.ChuckJoke
import ru.heatalways.chucknorrisfunfacts.core.presentation.base.MviState
import ru.heatalways.chucknorrisfunfacts.common.general.utils.StringResource
import ru.heatalways.chucknorrisfunfacts.common.presentation.utils.ScrollState
import ru.heatalways.chucknorrisfunfacts.common.presentation.utils.SnackbarState
import ru.heatalways.chucknorrisfunfacts.common.presentation.utils.ToastState

internal data class RandomJokeViewState(
    val isLoading: Boolean = false,
    val isJokeLoading: Boolean = false,
    val message: StringResource? = null,
    val jokes: List<ChuckJoke> = emptyList(),
    val category: Category = Category.Any,
    val scrollState: ScrollState = ScrollState.Stopped,
    val toastState: ToastState = ToastState.Hidden,
    val snackbarState: SnackbarState = SnackbarState.Hidden
): MviState