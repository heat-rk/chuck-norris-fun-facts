package ru.heatalways.chucknorrisfunfacts.presentation.screen.search_joke

import ru.heatalways.chucknorrisfunfacts.data.entities.ChuckJoke

sealed class SearchJokeState {
    object Loading: SearchJokeState()
    class Loaded(val jokes: List<ChuckJoke>): SearchJokeState()
    class Error(val message: String?): SearchJokeState()
}