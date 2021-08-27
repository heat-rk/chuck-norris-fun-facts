package ru.heatalways.chucknorrisfunfacts.domain.interactors.random_joke

import ru.heatalways.chucknorrisfunfacts.domain.utils.StringResource
import ru.heatalways.chucknorrisfunfacts.presentation.base.MviEffect

sealed class RandomJokeViewEffect: MviEffect {
    object ScrollUp: RandomJokeViewEffect()
    object NavigateToCategorySelectionScreen: RandomJokeViewEffect()
    data class Error(val message: StringResource?): RandomJokeViewEffect()
}