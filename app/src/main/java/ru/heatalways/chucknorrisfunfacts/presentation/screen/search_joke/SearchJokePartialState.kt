package ru.heatalways.chucknorrisfunfacts.presentation.screen.search_joke

import ru.heatalways.chucknorrisfunfacts.domain.models.ChuckJoke
import ru.heatalways.chucknorrisfunfacts.core.models.StringResource
import ru.heatalways.chucknorrisfunfacts.presentation.util.ScrollState

sealed class SearchJokePartialState {
    data class Scroll(val scrollState: ScrollState): SearchJokePartialState()

    object JokesLoading: SearchJokePartialState()
    data class JokesMessage(val message: StringResource): SearchJokePartialState()
    data class JokesLoaded(val jokes: List<ChuckJoke>): SearchJokePartialState()
}
