package ru.heatalways.chucknorrisfunfacts.domain.interactors.search_joke

import ru.heatalways.chucknorrisfunfacts.data.entities.ChuckJoke
import ru.heatalways.chucknorrisfunfacts.data.utils.StringResource

sealed class SearchJokePartialState {
    object Loading: SearchJokePartialState()
    class Message(val message: StringResource): SearchJokePartialState()
    class Jokes(val jokes: List<ChuckJoke>): SearchJokePartialState()
}
