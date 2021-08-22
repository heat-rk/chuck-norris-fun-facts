package ru.heatalways.chucknorrisfunfacts.business.interactors.search_joke

import ru.heatalways.chucknorrisfunfacts.business.domain.repositories.chuck_norris_jokes.ChuckJoke
import ru.heatalways.chucknorrisfunfacts.business.domain.utils.StringResource
import ru.heatalways.chucknorrisfunfacts.presentation.base.MviState

data class SearchJokeViewState(
    val isLoading: Boolean = false,
    val message: StringResource? = null,
    val jokes: List<ChuckJoke> = emptyList()
): MviState
