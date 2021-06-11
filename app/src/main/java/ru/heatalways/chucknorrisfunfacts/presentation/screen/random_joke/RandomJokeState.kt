package ru.heatalways.chucknorrisfunfacts.presentation.screen.random_joke

import ru.heatalways.chucknorrisfunfacts.data.entities.ChuckJoke

sealed class RandomJokeState {
    object JokeLoading: RandomJokeState()
    class JokesLoaded(val jokes: List<ChuckJoke>): RandomJokeState()
    class JokeLoadError(val message: String?): RandomJokeState()
}