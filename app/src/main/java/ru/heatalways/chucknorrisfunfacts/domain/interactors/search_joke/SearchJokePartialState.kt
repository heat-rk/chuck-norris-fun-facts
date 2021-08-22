package ru.heatalways.chucknorrisfunfacts.domain.interactors.search_joke

import ru.heatalways.chucknorrisfunfacts.domain.repositories.chuck_norris_jokes.ChuckJoke
import ru.heatalways.chucknorrisfunfacts.domain.utils.StringResource

sealed class SearchJokePartialState {
    object Loading: SearchJokePartialState()
    class Message(val message: StringResource): SearchJokePartialState()
    class Jokes(val jokes: List<ChuckJoke>): SearchJokePartialState()
}
