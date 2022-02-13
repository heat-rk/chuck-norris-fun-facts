package ru.heatalways.chucknorrisfunfacts.random_joke.presentation.screen.random_joke

import ru.heatalways.chucknorrisfunfacts.core.presentation.base.MviAction

internal sealed class RandomJokeAction: MviAction {
    object NextPage: RandomJokeAction()
    object RequestRandomJoke: RandomJokeAction()
    object SelectCategory: RandomJokeAction()
    object RestoreJokes: RandomJokeAction()
    data class ToolbarItemSelect(val itemId: Int): RandomJokeAction()
}