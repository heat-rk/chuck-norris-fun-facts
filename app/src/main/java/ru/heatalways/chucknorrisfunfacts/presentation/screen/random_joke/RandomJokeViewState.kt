package ru.heatalways.chucknorrisfunfacts.presentation.screen.random_joke

import ru.heatalways.chucknorrisfunfacts.domain.repositories.chuck_norris_jokes.Category
import ru.heatalways.chucknorrisfunfacts.domain.repositories.chuck_norris_jokes.ChuckJoke
import ru.heatalways.chucknorrisfunfacts.domain.utils.SnackbarState
import ru.heatalways.chucknorrisfunfacts.domain.utils.StringResource
import ru.heatalways.chucknorrisfunfacts.domain.utils.ToastState
import ru.heatalways.chucknorrisfunfacts.presentation.base.MviState

data class RandomJokeViewState(
    val isLoading: Boolean = false,
    val isJokeLoading: Boolean = false,
    val message: StringResource? = null,
    val jokes: List<ChuckJoke> = emptyList(),
    val category: Category = Category.Any,
    val isScrollingUp: Boolean = false,
    val toastState: ToastState = ToastState.Hidden,
    val snackbarState: SnackbarState = SnackbarState.Hidden
): MviState