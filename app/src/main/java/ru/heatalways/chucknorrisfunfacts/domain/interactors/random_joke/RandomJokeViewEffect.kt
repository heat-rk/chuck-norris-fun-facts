package ru.heatalways.chucknorrisfunfacts.domain.interactors.random_joke

import ru.heatalways.chucknorrisfunfacts.domain.utils.StringResource
import ru.heatalways.chucknorrisfunfacts.presentation.base.MviEffect

sealed class RandomJokeViewEffect: MviEffect {
    object NavigateToCategorySelectionScreen: RandomJokeViewEffect()
    class Error(val message: StringResource?): RandomJokeViewEffect()
}