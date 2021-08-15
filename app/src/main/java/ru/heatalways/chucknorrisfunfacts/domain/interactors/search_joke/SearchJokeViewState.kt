package ru.heatalways.chucknorrisfunfacts.domain.interactors.search_joke

import ru.heatalways.chucknorrisfunfacts.data.entities.ChuckJoke
import ru.heatalways.chucknorrisfunfacts.data.utils.StringResource
import ru.heatalways.chucknorrisfunfacts.presentation.base.MviState

data class SearchJokeViewState(
    val isLoading: Boolean = false,
    val message: StringResource? = null,
    val jokes: List<ChuckJoke> = emptyList()
): MviState
