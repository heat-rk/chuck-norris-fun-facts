package ru.heatalways.chucknorrisfunfacts.domain.interactors.random_joke

import ru.heatalways.chucknorrisfunfacts.presentation.base.MviAction

sealed class RandomJokeAction: MviAction {
    object OnRandomJokeRequest: RandomJokeAction()
    object OnCategorySelectionButtonClick: RandomJokeAction()
}