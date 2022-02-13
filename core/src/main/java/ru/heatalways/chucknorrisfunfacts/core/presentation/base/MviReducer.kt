package ru.heatalways.chucknorrisfunfacts.core.presentation.base


interface MviReducer<State, PartialState> {
    fun reduce(state: State, partialState: PartialState): State
}