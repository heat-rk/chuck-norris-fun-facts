package ru.heatalways.chucknorrisfunfacts.domain.interactors.search_joke

import ru.heatalways.chucknorrisfunfacts.presentation.base.MviEffect

sealed class SearchJokeViewEffect: MviEffect {
    object ScrollUp: SearchJokeViewEffect()
}
