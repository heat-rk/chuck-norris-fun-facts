package ru.heatalways.chucknorrisfunfacts.presentation.screen.random_joke

import ru.heatalways.chucknorrisfunfacts.presentation.base.MviAction

sealed class RandomJokeAction: MviAction {
    object NextPage: RandomJokeAction()
    object RequestRandomJoke: RandomJokeAction()
    object SelectCategory: RandomJokeAction()
    object RestoreJokes: RandomJokeAction()
    data class ToolbarItemSelect(val itemId: Int): RandomJokeAction()
}