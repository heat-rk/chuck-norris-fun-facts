package ru.heatalways.chucknorrisfunfacts.core.base


interface MviReducer<State, PartialState> {
    fun reduce(state: State, partialState: PartialState): State
}