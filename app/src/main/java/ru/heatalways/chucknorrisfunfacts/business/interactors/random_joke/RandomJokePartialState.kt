package ru.heatalways.chucknorrisfunfacts.business.interactors.random_joke

import ru.heatalways.chucknorrisfunfacts.business.domain.repositories.chuck_norris_jokes.Category
import ru.heatalways.chucknorrisfunfacts.business.domain.repositories.chuck_norris_jokes.ChuckJoke
import ru.heatalways.chucknorrisfunfacts.business.domain.utils.StringResource

sealed class RandomJokePartialState {
    object Loading: RandomJokePartialState()
    object JokeLoading: RandomJokePartialState()
    class Message(val message: StringResource): RandomJokePartialState()
    class JokeLoaded(val joke: ChuckJoke): RandomJokePartialState()
    class JokesLoaded(val jokes: List<ChuckJoke>): RandomJokePartialState()
    class CategorySelected(val category: Category): RandomJokePartialState()
}