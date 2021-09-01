package ru.heatalways.chucknorrisfunfacts.domain.interactors.random_joke

import ru.heatalways.chucknorrisfunfacts.domain.repositories.chuck_norris_jokes.Category
import ru.heatalways.chucknorrisfunfacts.domain.repositories.chuck_norris_jokes.ChuckJoke
import ru.heatalways.chucknorrisfunfacts.domain.utils.StringResource

sealed class RandomJokePartialState {
    data class Message(val message: StringResource): RandomJokePartialState()

    object JokeLoading: RandomJokePartialState()
    data class JokeLoaded(val joke: ChuckJoke): RandomJokePartialState()
    data class JokeLoadingError(val message: StringResource): RandomJokePartialState()

    object Loading: RandomJokePartialState()
    data class JokesLoaded(val jokes: List<ChuckJoke>): RandomJokePartialState()
    object JokesUpdating: RandomJokePartialState()
    data class JokesUpdated(val jokes: List<ChuckJoke>): RandomJokePartialState()

    data class CategorySelected(val category: Category): RandomJokePartialState()
}