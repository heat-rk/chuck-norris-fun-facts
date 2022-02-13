package ru.heatalways.chucknorrisfunfacts.search_joke.presentation.screen.search_joke

import ru.heatalways.chucknorrisfunfacts.core.domain.models.ChuckJoke
import ru.heatalways.chucknorrisfunfacts.core.presentation.base.MviState
import ru.heatalways.chucknorrisfunfacts.common.general.utils.StringResource
import ru.heatalways.chucknorrisfunfacts.common.presentation.utils.ScrollState

internal data class SearchJokeViewState(
    val isJokesLoading: Boolean = false,
    val jokesMessage: StringResource? = null,
    val jokes: List<ChuckJoke> = emptyList(),
    val scrollState: ScrollState = ScrollState.Stopped
): MviState
