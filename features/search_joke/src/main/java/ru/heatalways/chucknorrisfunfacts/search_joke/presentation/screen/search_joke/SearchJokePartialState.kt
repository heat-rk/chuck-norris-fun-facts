package ru.heatalways.chucknorrisfunfacts.search_joke.presentation.screen.search_joke

import ru.heatalways.chucknorrisfunfacts.common.general.utils.StringResource
import ru.heatalways.chucknorrisfunfacts.common.presentation.utils.ScrollState
import ru.heatalways.chucknorrisfunfacts.core.domain.models.ChuckJoke

internal sealed class SearchJokePartialState {
    data class Scroll(val scrollState: ScrollState): SearchJokePartialState()

    object JokesLoading: SearchJokePartialState()
    data class JokesMessage(val message: StringResource): SearchJokePartialState()
    data class JokesLoaded(val jokes: List<ChuckJoke>): SearchJokePartialState()
}
