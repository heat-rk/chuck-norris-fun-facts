package ru.heatalways.chucknorrisfunfacts.domain.interactors.random_joke

import ru.heatalways.chucknorrisfunfacts.domain.repositories.chuck_norris_jokes.Category
import ru.heatalways.chucknorrisfunfacts.domain.repositories.chuck_norris_jokes.ChuckJoke
import ru.heatalways.chucknorrisfunfacts.domain.utils.StringResource

sealed class RandomJokePartialState {
    object Loading: RandomJokePartialState()
    object JokeLoading: RandomJokePartialState()
    class Message(val message: StringResource): RandomJokePartialState()
    class JokeLoaded(val joke: ChuckJoke): RandomJokePartialState()
    class JokeLoadingError(val message: StringResource): RandomJokePartialState()
    class JokesLoaded(val jokes: List<ChuckJoke>): RandomJokePartialState()
    class CategorySelected(val category: Category): RandomJokePartialState()
}