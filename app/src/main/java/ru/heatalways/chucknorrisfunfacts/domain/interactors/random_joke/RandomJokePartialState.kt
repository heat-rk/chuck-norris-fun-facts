package ru.heatalways.chucknorrisfunfacts.domain.interactors.random_joke

import ru.heatalways.chucknorrisfunfacts.data.entities.Category
import ru.heatalways.chucknorrisfunfacts.data.entities.ChuckJoke
import ru.heatalways.chucknorrisfunfacts.data.utils.StringResource

sealed class RandomJokePartialState {
    object Loading: RandomJokePartialState()
    object JokeLoading: RandomJokePartialState()
    class Message(val message: StringResource): RandomJokePartialState()
    class JokeLoaded(val joke: ChuckJoke): RandomJokePartialState()
    class JokesLoaded(val jokes: List<ChuckJoke>): RandomJokePartialState()
    class CategorySelected(val category: Category): RandomJokePartialState()
}