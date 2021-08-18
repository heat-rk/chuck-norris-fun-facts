package ru.heatalways.chucknorrisfunfacts.business.interactors.random_joke

import ru.heatalways.chucknorrisfunfacts.presentation.base.MviEffect

sealed class RandomJokeViewEffect: MviEffect {
    object NavigateToCategorySelectionScreen: RandomJokeViewEffect()
    class Error(val message: String?): RandomJokeViewEffect()
}