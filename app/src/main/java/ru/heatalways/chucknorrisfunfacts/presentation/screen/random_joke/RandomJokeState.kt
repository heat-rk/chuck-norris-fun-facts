package ru.heatalways.chucknorrisfunfacts.presentation.screen.random_joke

import ru.heatalways.chucknorrisfunfacts.data.entities.ChuckJoke

sealed class RandomJokeState {
    object Loading: RandomJokeState()
    class Loaded(val jokes: List<ChuckJoke>): RandomJokeState()
    class Error(val message: String?): RandomJokeState()
}