package ru.heatalways.chucknorrisfunfacts.presentation.screen.search_joke

import ru.heatalways.chucknorrisfunfacts.domain.repositories.chuck_norris_jokes.ChuckJoke
import ru.heatalways.chucknorrisfunfacts.domain.utils.StringResource
import ru.heatalways.chucknorrisfunfacts.presentation.base.MviState

data class SearchJokeViewState(
    val isJokesLoading: Boolean = false,
    val jokesMessage: StringResource? = null,
    val jokes: List<ChuckJoke> = emptyList(),
    val isScrollingUp: Boolean = false
): MviState
