package ru.heatalways.chucknorrisfunfacts.presentation.base


interface MviReducer<State, PartialState> {
    fun reduce(state: State, partialState: PartialState): State
}