package ru.heatalways.chucknorrisfunfacts.business.interactors.random_joke

import ru.heatalways.chucknorrisfunfacts.presentation.base.MviReducer

object RandomJokeStateReducer: MviReducer<RandomJokeViewState, RandomJokePartialState>({ partialState ->
        when (partialState) {
            is RandomJokePartialState.JokeLoaded -> {
                copy(
                    isJokeLoading = false,
                    message = null,
                    jokes = listOf(partialState.joke) + jokes
                )
            }
            is RandomJokePartialState.JokeLoading -> {
                copy(
                    isJokeLoading = true
                )
            }
            is RandomJokePartialState.JokesLoaded -> {
                copy(
                    isLoading = false,
                    message = null,
                    jokes = partialState.jokes
                )
            }
            is RandomJokePartialState.Loading -> {
                copy(
                    isLoading = true,
                    message = null
                )
            }
            is RandomJokePartialState.Message -> {
                copy(
                    isLoading = false,
                    message = partialState.message
                )
            }
            is RandomJokePartialState.CategorySelected -> {
                copy(
                    category = partialState.category
                )
            }
        }
    })