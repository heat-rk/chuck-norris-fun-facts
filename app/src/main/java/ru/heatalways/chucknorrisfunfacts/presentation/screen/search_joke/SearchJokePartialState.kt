package ru.heatalways.chucknorrisfunfacts.presentation.screen.search_joke

import ru.heatalways.chucknorrisfunfacts.domain.models.ChuckJoke
import ru.heatalways.chucknorrisfunfacts.domain.utils.StringResource

sealed class SearchJokePartialState {
    data class ScrollUp(val isScrolling: Boolean): SearchJokePartialState()

    object JokesLoading: SearchJokePartialState()
    data class JokesMessage(val message: StringResource): SearchJokePartialState()
    data class JokesLoaded(val jokes: List<ChuckJoke>): SearchJokePartialState()
}
