package ru.heatalways.chucknorrisfunfacts.presentation.screen.search_joke

import ru.heatalways.chucknorrisfunfacts.domain.models.ChuckJoke
import ru.heatalways.chucknorrisfunfacts.core.utils.StringResource
import ru.heatalways.chucknorrisfunfacts.presentation.base.MviState
import ru.heatalways.chucknorrisfunfacts.presentation.util.ScrollState

data class SearchJokeViewState(
    val isJokesLoading: Boolean = false,
    val jokesMessage: StringResource? = null,
    val jokes: List<ChuckJoke> = emptyList(),
    val scrollState: ScrollState = ScrollState.Stopped
): MviState
