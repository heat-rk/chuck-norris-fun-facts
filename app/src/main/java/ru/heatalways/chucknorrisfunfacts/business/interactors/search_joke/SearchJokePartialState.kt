package ru.heatalways.chucknorrisfunfacts.business.interactors.search_joke

import ru.heatalways.chucknorrisfunfacts.business.domain.repositories.chuck_norris_jokes.ChuckJoke
import ru.heatalways.chucknorrisfunfacts.business.domain.utils.StringResource

sealed class SearchJokePartialState {
    object Loading: SearchJokePartialState()
    class Message(val message: StringResource): SearchJokePartialState()
    class Jokes(val jokes: List<ChuckJoke>): SearchJokePartialState()
}
